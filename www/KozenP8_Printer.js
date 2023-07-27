var exec = require('cordova/exec');

var KozenP8_Printer = {
    state: function (fnSuccess, fnError) {
        exec(fnSuccess, fnError, "KozenP8_Printer", "state", []);
    },
    list: function (fnSuccess, fnError) {
        exec(fnSuccess, fnError, "KozenP8_Printer", "list", []);
    },
    connect: function (fnSuccess, fnError, name) {
        exec(fnSuccess, fnError, "KozenP8_Printer", "connect", [name]);
    },
    connected: function (fnSuccess, fnError) {
        exec(fnSuccess, fnError, "KozenP8_Printer", "connected", []);
    },
    disconnect: function (fnSuccess, fnError) {
        exec(fnSuccess, fnError, "KozenP8_Printer", "disconnect", []);
    },
    setEncoding: function (fnSuccess, fnError, str) {
        exec(fnSuccess, fnError, "KozenP8_Printer", "setEncoding", [str]);
    },
    printText: function (fnSuccess, fnError, str) {
        exec(fnSuccess, fnError, "KozenP8_Printer", "printText", [str]);
    },
    printTextSizeAlign: function (fnSuccess, fnError, str, size, align) {
        exec(fnSuccess, fnError, "KozenP8_Printer", "printTextSizeAlign", [str, size, align]);
    },
    printTitle: function (fnSuccess, fnError, str, size, align) {
        exec(fnSuccess, fnError, "KozenP8_Printer", "printTitle", [str, size, align]);
    },
    printImageUrl: function (fnSuccess, fnError, str, align) {
        exec(fnSuccess, fnError, "KozenP8_Printer", "printImageUrl", [str, align]);
    },
    printBase64: function (fnSuccess, fnError, str, align) {
        exec(fnSuccess, fnError, "KozenP8_Printer", "printBase64", [str, align]);
    },
    printPOSCommand: function (fnSuccess, fnError, str) {
        exec(fnSuccess, fnError, "KozenP8_Printer", "printPOSCommand", [str]);
    },
    printQRCode: function (fnSuccess, fnError, data, align, model, size, eclevel) {
        exec(fnSuccess, fnError, "KozenP8_Printer", "printQRCode", [data, align, model, size, eclevel]);
    },
    printBarcode: function (fnSuccess, fnError, system, data, align, position, font, height) {
        exec(fnSuccess, fnError, "KozenP8_Printer", "printBarcode", [system, data, align, position, font, height]);
    }
};

module.exports = KozenP8_Printer;
