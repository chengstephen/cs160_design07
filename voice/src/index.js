'use strict';

var APP_ID = '';
var Alexa = require('alexa-sdk');

var STATES = {
    MAIN: '_MAINMODE',
    URGENT: '_URGENTMODE',
    SCHEDULE: '_SCHEDULEMODE',
    REQUEST: '_REQUESTMODE'
};

var time = new Date();

var languageString = {
    'en': {
        'translation': {
            'WELCOME': "baymax here, i can help with urgent problems, scheduling, or any special requests. ",
            'NEED_HELP': "what do you need help with?",
            'MAIN_HELP': "to get help, you can say 'urgent problem', 'scheduling', or 'special request'",
            'URGENT_PROMPT': "okay, what is your problem? if it's an emergency say, 'emergency'.",
            'EMERGENCY_RESPONSE': "okay, medical staff have been notified and will be arriving immediately",
            'READ_REQUEST': "okay, your request is '%s'. the nurse will be here shortly",
            'URGENT_HELP': "for emergencies say 'emergency', for other urgent help, just tell me what you need",
            'SCHEDULE_PROMPT': "to hear the next item on your schedule, say next",
            'SCHEDULE': [
                            {
                                'date': (new Date()).setMinutes(time.getMinutes() + 15),
                                'appointment': 'take 10 milligrams of aspirin'
                            },
                            {
                                'date': (new Date()).setMinutes(time.getMinutes() + 30),
                                'appointment': 'blood pressure reading with nurse'
                            },
                            {
                                'date': (new Date()).setMinutes(time.getMinutes() + 60),
                                'appointment': 'lunch'
                            }
                        ],
            'READ_SCHEDULE': "%s in %s minutes",
            'END_SCHEDULE': "there are no more items in your schedule today. to hear your schedule again, just say next.",
            'NO_PREVIOUS': "there's no previous item, please say next to hear the next item on your schedule.",
            'REQUEST_HELP': "",
            'TRY_AGAIN': "i didn't quite catch that, could you repeat it?"

        }
    }
};

exports.handler = function (event, context) {

    var alexa = Alexa.handler(event, context);
    alexa.appId = APP_ID;
    alexa.resources = languageString;
    alexa.registerHandlers(newSessionHandlers, mainStateHandlers, urgentStateHandlers, scheduleStateHandlers, requestStateHandlers);
    alexa.execute();

};

var newSessionHandlers = {
    'LaunchRequest': function () {
        this.handler.state = STATES.MAIN;
        this.emitWithState('start', true);
    },

    'UrgentIntent': function () {
        this.handler.state = STATES.MAIN;
        this.emitWithState('UrgentIntent');
    },

    'SchedulingIntent': function () {
        this.handler.state = STATES.MAIN;
        this.emitWithState('SchedulingIntent');
    },

    'RequestIntent': function () {
        this.handler.state = STATES.MAIN;
        this.emitWithState('RequestIntent');
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

    'UrgentIntent': function () {
        this.handler.state = STATES.URGENT;
        this.emitWithState('start');
    },

    'ScheduleIntent': function () {
        this.handler.state = STATES.SCHEDULING;
        this.emitWithState('start');
    },

    'RequestIntent': function () {
        this.handler.state = STATES.REQUEST;
        this.emitWithState('start');
    },

    'HelpIntent': function () {
        this.emit(':ask', this.t('MAIN_HELP'));
    },

    'Unhandled': function () {
        this.emitWithState('HelpIntent');
    }

});

var urgentStateHandlers = Alexa.CreateStateHandler(STATES.URGENT, {
    'start': function (newSession) {
        var speechOutput = '';
        speechOutput += this.t('URGENT_PROMPT');
        this.emit(':ask', speechOutput);
    },

    'NewRequestIntent': function () {
        var newRequest = this.event.request.intent.slots.Request;
        if (newRequest && newRequest.value) {
            newRequest = newRequest.value.toLowerCase();
            this.emit(':tell', this.t('READ_REQUEST', newRequest));
        } else {
            this.emit(':ask', this.t('NO_PREVIOUS'));
        }
    },

    'EmergencyIntent': function () {
        this.emit(':tell', this.t('EMERGENCY_RESPONSE'));
    },

    'MainMenuIntent': function () {
        this.handler.state = STATES.MAIN;
        this.emitWithState('start', false);
    },

    'ScheduleIntent': function () {
        this.handler.state = STATES.SCHEDULING;
        this.emitWithState('start');
    },

    'RequestIntent': function () {
        this.handler.state = STATES.REQUEST;
        this.emitWithState('start');
    },

    'HelpIntent': function () {
        this.emit(':ask', this.t('URGENT_HELP'));
    },

    'Unhandled': function () {
        this.emitWithState('HelpIntent');
    }

});

var scheduleStateHandlers = Alexa.CreateStateHandler(STATES.SCHEDULE, {
    'start': function (newSession) {
        this.attributes[scheduleCount] = 0;
        var speechOutput = '';
        speechOutput += this.t('SCHEDULE_PROMPT');
        this.emit(':ask', speechOutput);
    },

    'NextIntent': function () {
        var schedule = this.t('SCHEDULE');
        var scheduleCount = this.attributes[scheduleCount];
        if (scheduleCount == schedule.length) {
            this.attributes[scheduleCount] = 0;
            this.emit(':ask', 'END_SCHEDULE');
        } else if (schedule[scheduleCount][0] - new Date() <= 0) {
            this.attributes[scheduleCount]++;
            this.emitWithState('NextIntent');
        } else {
            this.attributes[scheduleCount]++;
            var i = schedule[scheduleCount];
            var ms = i[0].getMinutes() - new Date();
            var minutes = Math.floor((ms / 1000) / 60);
            this.emit(':ask', this.t('READ_APPOINTMENT', i[1], minutes));
        }
    },

    'StartOverIntent': function () {
        this.attributes[scheduleCount] = 0;
        this.emitWithState('NextIntent');
    },

    'PreviousIntent': function () {
        if (this.attributes[scheduleCount] > 0) {
            this.attributes[scheduleCount]--;
            this.emitWithState('NextIntent');
        } else {
            this.emit(':ask', this.t('NO_PREVIOUS'))
        }
    },

    'MainMenuIntent': function () {
        this.handler.state = STATES.MAIN;
        this.emitWithState('start', false);
    },

    'UrgentIntent': function () {
        this.handler.state = STATES.URGENT;
        this.emitWithState('start');
    },

    'RequestIntent': function () {
        this.handler.state = STATES.REQUEST;
        this.emitWithState('start');
    },

    'HelpIntent': function () {
        this.emit(':ask', this.t('SCHEDULE_PROMPT'));
    },

    'Unhandled': function () {
        this.emitWithState('HelpIntent');
    }

});


var requestStateHandlers = Alexa.CreateStateHandler(STATES.REQUEST, {
    'start': function (newSession) {
        var speechOutput = '';
        speechOutput += this.t('REQUEST_PROMPT');
        this.emit(':ask', speechOutput);
    },



    'MainMenuIntent': function () {
        this.handler.state = STATES.MAIN;
        this.emitWithState('start', false);
    },

    'UrgentIntent': function () {
        this.handler.state = STATES.URGENT;
        this.emitWithState('start');
    },

    'ScheduleIntent': function () {
        this.handler.state = STATES.SCHEDULE;
        this.emitWithState('start');
    },

    'HelpIntent': function () {
        this.emit(':ask', this.t('REQUEST_HELP'));
    },

    'Unhandled': function () {
        this.emitWithState('HelpIntent');
    }

});

function sortByDateHelper(a, b) {
    return a['date'] - b['date'];
}
