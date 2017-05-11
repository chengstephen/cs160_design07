'use strict';

var APP_ID = 'amzn1.ask.skill.7195510a-6126-40f7-8013-dccd7118bc12';
var Alexa = require('alexa-sdk');
var https = require('https');

var STATES = {
    MAIN: '_MAINMODE',
    PRIORITY: '_PRIORITYMODE',
    URGENT: '_URGENTMODE',
    SCHEDULE: '_SCHEDULEMODE',
    INVENTORY: '_INVENTORYMODE'
};

var time = new Date();

var languageString = {
    'en': {
        'translation': {
            'WELCOME': "nurse assistant here, i can help with urgent problems, scheduling, or inventory requests. ",
            'NEED_HELP': "what can i help you with today?",
            'MAIN_HELP': "to get help, you can say 'urgent problem', 'scheduling', or 'inventory request'",
            '911': "emergency care is on the way",

            'URGENT_OR_INVENTORY': "Is your request for urgent help or for an item?",

            'URGENT_PROMPT': "okay, what is your problem?",
            'READ_URGENT_REQUEST': "okay, your request is, '%s'. the nurse will be here shortly",
            'URGENT_HELP': "for emergencies say 'emergency', for other urgent help, just tell me what you need. return to the main menu by saying, main menu.",

            'SCHEDULE_PROMPT': "to hear the next item on your schedule, say next",
            'SCHEDULE': [
                            {
                                'Time': (new Date()).setMinutes(time.getMinutes() + 15),
                                'Appointment': 'take 10 milligrams of aspirin'
                            },
                            {
                                'Time': (new Date()).setMinutes(time.getMinutes() + 30),
                                'Appointment': 'blood pressure reading with nurse'
                            },
                            {
                                'Time': (new Date()).setMinutes(time.getMinutes() + 60),
                                'Appointment': 'lunch'
                            }
                        ],
            'READ_SCHEDULE': "%s in %s minutes",
            'END_SCHEDULE': "there are no more items in your schedule today. to hear your schedule again, just say next.",
            'NO_PREVIOUS': "there's no previous item, please say next to hear the next item on your schedule.",
            'SCHEDULE_HELP': "to hear the next item on your schedule, say next. return to the main menu by saying, main menu.",

            'INVENTORY_PROMPT': "okay, what item do you need?",
            'READ_INVENTORY_REQUEST': "okay, your request is, '%s'. the nurse will update you soon about the status of your request",
            'INVENTORY_HELP': "make an inventory request by saying 'i need an item'. return to the main menu by saying, main menu.",


            'TRY_AGAIN': "i didn't quite catch that, could you repeat it?",
            'GOODBYE': "see you next time"
        }
    }
};

exports.handler = function (event, context) {

    var alexa = Alexa.handler(event, context);
    alexa.appId = APP_ID;
    alexa.resources = languageString;
    alexa.registerHandlers(newSessionHandlers, mainStateHandlers, priorityStateHandlers, urgentStateHandlers, scheduleStateHandlers, inventoryStateHandlers);
    alexa.execute();

};

var newSessionHandlers = {
    'LaunchRequest': function () {
        this.handler.state = STATES.MAIN;
        this.emitWithState('start', true);
    },

    'NewRequestIntent': function () {
        this.handler.state = STATES.MAIN;
        this.emitWithState('NewRequestIntent');
    },

    'UrgentIntent': function () {
        this.handler.state = STATES.MAIN;
        this.emitWithState('UrgentIntent');
    },

    'ScheduleIntent': function () {
        this.handler.state = STATES.MAIN;
        this.emitWithState('ScheduleIntent');
    },

    'InventoryIntent': function () {
        this.handler.state = STATES.MAIN;
        this.emitWithState('InventoryIntent');
    },

    'EmergencyIntent': function () {
        this.handler.state = STATES.MAIN;
        this.emitWithState('EmergencyIntent');
    },
    
    'Unhandled': function () {
        this.emit('LaunchRequest');
    }
};

var mainStateHandlers = Alexa.CreateStateHandler(STATES.MAIN, {
    'start': function (newSession) {
        var speechOutput = '';
        if (newSession) {
            speechOutput += this.t('WELCOME');
        }
        speechOutput += this.t('NEED_HELP');
        this.emit(':ask', speechOutput);
    },

    'NewRequestIntent': function () {
        this.handler.state = STATES.PRIORITY;
        this.emitWithState('start');
    },

    'UrgentIntent': function () {
        this.handler.state = STATES.URGENT;
        this.emitWithState('start');
    },

    'ScheduleIntent': function () {
        this.handler.state = STATES.SCHEDULE;
        this.emitWithState('start');
    },

    'InventoryIntent': function () {
        this.handler.state = STATES.INVENTORY;
        this.emitWithState('start');
    },

    'EmergencyIntent': function () {
        this.emit(':tell', this.t('911'));
    },

    'HelpIntent': function () {
        this.emit(':ask', this.t('MAIN_HELP'));
    },

    'ExitIntent': function () {
        this.emit(':tell', this.t('GOODBYE'));
    },

    'Unhandled': function () {
        this.emitWithState('HelpIntent');
    }

});

var priorityStateHandlers = Alexa.CreateStateHandler(STATES.PRIORITY, {
    'start': function () {
        var newRequest = this.event.request.intent.slots.Request;
        if (newRequest && newRequest.value) {
            this.attributes['currentRequest'] = newRequest.value.toLowerCase();
            this.emit(':ask', this.t('URGENT_OR_INVENTORY'));
        } else {
            this.handler.state = STATES.MAIN;
            this.emitWithState('start');
        }
    },

    'UrgentIntent': function () {
        this.handler.state = STATES.URGENT;
        this.emitWithState('NewRequestIntent', this.attributes['currentRequest']);
    },

    'InventoryIntent': function () {
        this.handler.state = STATES.INVENTORY;
        this.emitWithState('NewRequestIntent', this.attributes['currentRequest']);
    }

});

var urgentStateHandlers = Alexa.CreateStateHandler(STATES.URGENT, {
    'start': function () {
        var speechOutput = this.t('URGENT_PROMPT');
        this.emit(':ask', speechOutput);
    },

    'NewRequestIntent': function (request) {
        var newRequest;
        if (typeof request !== 'undefined') {
            newRequest = request;
        } else {
            newRequest = this.event.request.intent.slots.Request;
            if (newRequest && newRequest.value) {
                newRequest = newRequest.value.toLowerCase();
            } else {
                this.emit(':ask', this.t('TRY_AGAIN'));
            }
        }
        var that = this;
        postUrgentRequest(newRequest, function() {
            that.emit(':tell', that.t('READ_URGENT_REQUEST', newRequest));
        });
    },

    'MainMenuIntent': function () {
        this.handler.state = STATES.MAIN;
        this.emitWithState('start', false);
    },

    'EmergencyIntent': function () {
        this.handler.state = STATES.MAIN;
        this.emitWithState('EmergencyIntent');
    },

    'HelpIntent': function () {
        this.emit(':ask', this.t('URGENT_HELP'));
    },

    'ExitIntent': function () {
        this.emit(':tell', this.t('GOODBYE'));
    },

    'Unhandled': function () {
        this.emitWithState('HelpIntent');
    }

});

var scheduleStateHandlers = Alexa.CreateStateHandler(STATES.SCHEDULE, {
    'start': function () {
        this.attributes['scheduleCount'] = -1;
        var speechOutput = this.t('SCHEDULE_PROMPT');
        this.emit(':ask', speechOutput);
    },

    'readAppointment': function (index) {
        var schedule = this.t('SCHEDULE');
        var i = schedule[index];
        var diff = i.Time - new Date();
        var minutes = Math.floor((diff / 1000) / 60);
        this.emit(':ask', this.t('READ_SCHEDULE', i.Appointment, minutes));
    },

    'NextIntent': function () {
        var schedule = this.t('SCHEDULE');
        if (this.attributes['scheduleCount'] == schedule.length) {
            this.attributes['scheduleCount'] = 0;
            this.emit(':ask', 'END_SCHEDULE');
        } else if (this.attributes['scheduleCount'] >= 0 && schedule[this.attributes['scheduleCount']].Time - new Date() <= 0) {
            this.attributes['scheduleCount']++;
            this.emitWithState('NextIntent');
        } else {
            this.attributes['scheduleCount']++;
            this.emitWithState('readAppointment', this.attributes['scheduleCount']);
        }
    },

    'StartOverIntent': function () {
        this.attributes['scheduleCount'] = -1;
        this.emitWithState('NextIntent');
    },

    'PreviousIntent': function () {
        if (this.attributes['scheduleCount'] > 0) {
            this.attributes['scheduleCount']--;
            this.emitWithState('readAppointment', this.attributes['scheduleCount']);
        } else {
            this.emit(':ask', this.t('NO_PREVIOUS'))
        }
    },

    'MainMenuIntent': function () {
        this.handler.state = STATES.MAIN;
        this.emitWithState('start', false);
    },

    'EmergencyIntent': function () {
        this.handler.state = STATES.MAIN;
        this.emitWithState('EmergencyIntent');
    },

    'HelpIntent': function () {
        this.emit(':ask', this.t('SCHEDULE_HELP'));
    },

    'ExitIntent': function () {
        this.emit(':tell', this.t('GOODBYE'));
    },

    'Unhandled': function () {
        this.emitWithState('HelpIntent');
    }

});


var inventoryStateHandlers = Alexa.CreateStateHandler(STATES.INVENTORY, {
    'start': function () {
        var speechOutput = this.t('INVENTORY_PROMPT');
        this.emit(':ask', speechOutput);
    },

    'NewRequestIntent': function (request) {
        var newRequest;
        if (typeof request !== 'undefined') {
            newRequest = request;
        } else {
            newRequest = this.event.request.intent.slots.Request;
            if (newRequest && newRequest.value) {
                newRequest = newRequest.value.toLowerCase();
            } else {
                this.emit(':ask', this.t('TRY_AGAIN'));
            }
        }
        this.emit(':tell', this.t('READ_INVENTORY_REQUEST', newRequest));
    },

    'MainMenuIntent': function () {
        this.handler.state = STATES.MAIN;
        this.emitWithState('start', false);
    },

    'EmergencyIntent': function () {
        this.handler.state = STATES.MAIN;
        this.emitWithState('EmergencyIntent');
    },

    'HelpIntent': function () {
        this.emit(':ask', this.t('INVENTORY_HELP'));
    },

    'ExitIntent': function () {
        this.emit(':tell', this.t('GOODBYE'));
    },

    'Unhandled': function () {
        this.emitWithState('HelpIntent');
    }

});

function postUrgentRequest(request, callback) {
    var timeStamp = (new Date()).getTime();
    console.log(timeStamp);
    var postData = {
        Item: {
            "Emergency": request,
            "Time": timeStamp
        },
        TableName: "Emergencies"
    };

    var postOptions = {
        host:  'a2owklu6i1.execute-api.us-east-1.amazonaws.com',
        port: '443',
        path: '/prod/EmergenciesUpdate',
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        }
    };

    var postReq = https.request(postOptions, function(res) {
        callback();
    });

    postReq.write(JSON.stringify(postData));
    postReq.end();
}

