const functions = require('firebase-functions');

const admin = require('firebase-admin');

const path = require('path');

const os = require('os');

const fs = require('fs');

admin.initializeApp(functions.config().firebase);



const mkdirp = require('mkdirp-promise');

const gcs = require('@google-cloud/storage')({

    keyFilename: 'sdhs-71f96-firebase-adminsdk-x81z6-ddfbeddb1b.json'

});

const spawn = require('child-process-promise').spawn;

const LOCAL_TMP_FOLDER = '/tmp/';

const THUMB_MAX_HEIGHT = 500;

const THUMB_MAX_WIDTH = 500;

// Thumbnail prefix added to file names.

const THUMB_PREFIX = 'thumb_';

const ref = admin.database().ref();



exports.pushNotification = functions.database.ref('/hyderabad').onWrite(event => {

    console.log('Push notification event triggered');

    /* Grab the current value of what was written to the Realtime Database */

    var valueObject = event.data.val();

    var prev = event.data.previous.val();

    var eventSnapshot = event.data;

    var flh = eventSnapshot.child('Madhapur');
	

    if (flh.changed()) {



        /* Create a notification and data payload. They contain the notification information, and message to be sent respectively */

        const payload = {

            notification: {

                title: valueObject.flash,

                body: "New Request",

                sound: "default"

            },

            data: {

                title: valueObject.flash,

            }

        };

        /* Create an options object that contains the time to live for the notification and the priority. */

        const options = {

            priority: "high",

            timeToLive: 60 * 60 * 24 //24 hours 

        };

        console.log('Push notification event triggered : ' + valueObject.flash + ' prev is : ' + prev.flash + ' bool is : ' + flh.changed());



        return admin.messaging().sendToTopic("Madhapur", payload, options);

    }

});
exports.pushNotification = functions.database.ref('/hyderabad').onWrite(event => {

    console.log('Push notification event triggered');

    /* Grab the current value of what was written to the Realtime Database */

    var valueObject = event.data.val();

    var prev = event.data.previous.val();

    var eventSnapshot = event.data;

    var flh = eventSnapshot.child('kukatpally');


    if (flh.changed()) {



        /* Create a notification and data payload. They contain the notification information, and message to be sent respectively */

        const payload = {

            notification: {

                title: valueObject.flash,

                body: "New Request",

                sound: "default"

            },

            data: {

                title: valueObject.flash,

            }

        };

        /* Create an options object that contains the time to live for the notification and the priority. */

        const options = {

            priority: "high",

            timeToLive: 60 * 60 * 24 //24 hours 

        };

        console.log('Push notification event triggered : ' + valueObject.flash + ' prev is : ' + prev.flash + ' bool is : ' + flh.changed());



        return admin.messaging().sendToTopic("kukatpally", payload, options);

    }

});
exports.pushNotification = functions.database.ref('/hyderabad').onWrite(event => {

    console.log('Push notification event triggered');

    /* Grab the current value of what was written to the Realtime Database */

    var valueObject = event.data.val();

    var prev = event.data.previous.val();

    var eventSnapshot = event.data;

    var flh = eventSnapshot.child('Afzalgunj');


    if (flh.changed()) {



        /* Create a notification and data payload. They contain the notification information, and message to be sent respectively */

        const payload = {

            notification: {

                title: valueObject.flash,

                body: "New Request",

                sound: "default"

            },

            data: {

                title: valueObject.flash,

            }

        };

        /* Create an options object that contains the time to live for the notification and the priority. */

        const options = {

            priority: "high",

            timeToLive: 60 * 60 * 24 //24 hours 

        };

        console.log('Push notification event triggered : ' + valueObject.flash + ' prev is : ' + prev.flash + ' bool is : ' + flh.changed());



        return admin.messaging().sendToTopic("Afzalgunj", payload, options);

    }

});





/*exports.newpost = functions.database.ref('/posts/{postID}').onWrite(event => {

    var snapShot = event.data;

    if (!snapShot.child('thumbnail').exists()) {

        const bucket = gcs.bucket('gs://sdhs-71f96.appspot.com');

        const filePath = snapShot.child('filePath').val();

        const file = bucket.file(filePath);

        const filePathSplit = filePath.split('/');

        const fileName = filePathSplit.pop();

        const fileDir = filePathSplit.join('/') + (filePathSplit.length > 0 ? '/' : '');

        const thumbFilePath = `${fileDir}${THUMB_PREFIX}${fileName}`;

        const tempLocalDir = `${LOCAL_TMP_FOLDER}${fileDir}`;

        const tempLocalFile = `${tempLocalDir}${fileName}`;

        const tempLocalThumbFile = `${LOCAL_TMP_FOLDER}${thumbFilePath}`;

        const thumbFile = bucket.file(thumbFilePath);

        console.log("file path idhi " + filePath + " file name idhi " + fileName);



        // Create the temp directory where the storage file will be downloaded.

        return mkdirp(tempLocalDir).then(() => {

            // Download file from bucket.

            return bucket.file(filePath).download({

                destination: tempLocalFile

            });

        }).then(() => {

            console.log('The file has been downloaded to', tempLocalFile);

            // Generate a thumbnail using ImageMagick.

            return spawn('convert', [tempLocalFile, '-thumbnail', `${THUMB_MAX_WIDTH}x${THUMB_MAX_HEIGHT}>`, tempLocalThumbFile]);

        }).then(() => {

            console.log('Thumbnail created at', tempLocalThumbFile);

            // Uploading the Thumbnail.

            return bucket.upload(tempLocalThumbFile, {

                destination: thumbFilePath

            })

        }).then(() => {

            console.log('Thumbnail uploaded to Storage at', thumbFilePath);

        }).then(results => {

            console.log('Got Signed URL');

            const thumbResult = results[0];

            const originalResult = results[1];

            const thumbFileUrl = thumbResult[0];



            const fileUrl = originalResult[0];

            // Add the URLs to the Database

            return snapShot.push({

                path: fileUrl,

                thumbnail: thumbFileUrl

            });

        }).catch(reason => {

            console.error(reason);

            console.log('lollllll');

        });



    }

    console.log("Title is : " + JSON.stringify(snapShot, null, 3));

});*/

exports.generateThumbnail = functions.storage.object().onChange(event => {

    const fileBucket = event.data.bucket;

    const bucket = gcs.bucket(fileBucket);

    const filePath = event.data.name;

    const file = bucket.file(filePath);

    const filePathSplit = filePath.split('/');

    const fileName = filePathSplit.pop();

    const fileDir = filePathSplit.join('/') + (filePathSplit.length > 0 ? '/' : '');

    const thumbFilePath = `${fileDir}${THUMB_PREFIX}${fileName}`;

    const tempLocalDir = `${LOCAL_TMP_FOLDER}${fileDir}`;

    const tempLocalFile = `${tempLocalDir}${fileName}`;

    const tempLocalThumbFile = `${LOCAL_TMP_FOLDER}${thumbFilePath}`;

    const thumbFile = bucket.file(thumbFilePath);

    console.log("file path idhi " + filePath+" file name idhi "+fileName);

    // Exit if this is triggered on a file that is not an image.

    if (!event.data.contentType.startsWith('image/')) {

        console.log('This is not an image.');

        return;

    }



    // Exit if the image is already a thumbnail.

    if (fileName.startsWith(THUMB_PREFIX)) {

        console.log('Already a Thumbnail.');

        return;

    }



    // Exit if this is a move or deletion event.

    if (event.data.resourceState === 'not_exists') {

        console.log('This is a deletion event.');

        return;

    }



    // Create the temp directory where the storage file will be downloaded.

    return mkdirp(tempLocalDir).then(() => {

        // Download file from bucket.

        return bucket.file(filePath).download({

            destination: tempLocalFile

        });

    }).then(() => {

        console.log('The file has been downloaded to', tempLocalFile);

        // Generate a thumbnail using ImageMagick.

        return spawn('convert', [tempLocalFile, '-thumbnail', `${THUMB_MAX_WIDTH}x${THUMB_MAX_HEIGHT}>`, tempLocalThumbFile]);

    }).then(() => {

        console.log('Thumbnail created at', tempLocalThumbFile);

        // Uploading the Thumbnail.

        return bucket.upload(tempLocalThumbFile, {

            destination: thumbFilePath

        })

    }).then(() => {

        console.log('Thumbnail uploaded to Storage at', thumbFilePath);

    }).then(() => {

        const config = {

            action: 'read',

            expires: '03-01-2500'

        };

        // Get the Signed URL for the thumbnail and original images

        return Promise.all([

      thumbFile.getSignedUrl(config),

      file.getSignedUrl(config),

    ]);

    }).then(results => {

        console.log('Got Signed URL');

        const thumbResult = results[0];

        const originalResult = results[1];

        const thumbFileUrl = thumbResult[0];



        const fileUrl = originalResult[0];

        // Add the URLs to the Database

        return ref.child('images/'+fileName).set({

            path: fileUrl,

            thumbnail: thumbFileUrl

        });

    }).catch(reason => {

        console.error(reason);

    });

});

