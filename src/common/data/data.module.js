angular
    .module('sg.common.data', [
        'firebase',
        'sg.common.log'
    ])
    .constant('DATA_API_URL', 'https://sgatom.firebaseio.com/');
