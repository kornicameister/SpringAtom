_.mixin({
    'format': function format(str, args) {
        var newStr = str;
        _.forEach(args, function (val, key) {
            newStr = newStr.replace('{' + key + '}', val);
        });
        return newStr;
    }
});

