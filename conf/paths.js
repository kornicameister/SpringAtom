var path = require('path'),
  dir = __dirname,
  paths = {
    'SRC_DIR': path.join(dir, '../src'),
    'DIST_DIR': path.join(dir, '../build'),
    'VENDOR_LIB': path.join(dir, '../src/vendor'),
    'TARGET_JS_FILE': 'app.js',
    'TARGET_CSS_FILE': 'app.css',
    'TARGET_VIEW_JS_FILE': 'view.js',
    'TARGET_JS_LIB_FILE': 'lib.js',
    'TARGET_CSS_LIB_FILE': 'lib.css'
  };

module.exports = paths;
