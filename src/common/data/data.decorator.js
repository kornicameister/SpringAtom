angular
    .module('sg.common.data')
    .config(['$provide', function ($provide) {

        $provide.decorator("$firebaseObject", function ($delegate) {
            var _super = $delegate.prototype.$$update;

            $delegate.prototype.$$update = function (snap) {
                var changed = _super.call(this, snap);
                if (this.hasOwnProperty("date")) {
                    this._dateObj = new Date(this.date);
                }
                return changed;
            };

            $delegate.prototype.getDate = function () {
                return this._dateObj;
            };

            return $delegate;
        });

    }]);