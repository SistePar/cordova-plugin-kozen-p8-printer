var exec = require('cordova/exec');

var vFunctions = {
    open: function (fnSuccess, fnError) {
        exec(fnSuccess, fnError, "KozenP8Printer", "open", []);
    },
    close: function (fnSuccess, fnError) {
        exec(fnSuccess, fnError, "KozenP8Printer", "close", []);
    },
    status: function (fnSuccess, fnError) {
        exec(fnSuccess, fnError, "KozenP8Printer", "status", []);
    },
    addPrintLine: function (fnSuccess, fnError, text, align) {
        exec(fnSuccess, fnError, "KozenP8Printer", "addPrintLine", [text, align]);
    },
    addPrintLineSizeBold: function (fnSuccess, fnError, text, align, size, bold) {
        exec(fnSuccess, fnError, "KozenP8Printer", "addPrintLineSizeBold", [text, align, size, bold]);
    },
    addPrintBase64: function (fnSuccess, fnError, encodedImage, align) {
        exec(fnSuccess, fnError, "KozenP8Printer", "addPrintBase64", [encodedImage, align]);
    },
    beginPrint: function (fnSuccess, fnError) {
        exec(fnSuccess, fnError, "KozenP8Printer", "beginPrint", []);
    },
};

module.exports = vFunctions;


/*printImageUrl: function (fnSuccess, fnError, str, align) {
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
    }*/