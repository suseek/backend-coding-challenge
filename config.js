/******************************************************************************************

This will be copied to config.js by the CI Server as part of the test build process.

******************************************************************************************/

module.exports = {
	// Build target directory, this is where all the static files will end up
	target: "./expenses/src/main/resources/static",
	htmltarget: "./expenses/src/main/resources/static",

	// Font service url
	fonts: "//fast.fonts.net/jsapi/8f4aef36-1a46-44be-a573-99686bfcc33b.js",

	// The root directory for all api calls
	apiroot: "/expenses/api/v1",

	// Root directory for static content
	staticRoot: "/expenses"

};
