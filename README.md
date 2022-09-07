# Keystore Opener

This tool lists out the `serial number`, `alias`, `fingerprint` and `expiration date` for each public key certificates 
contained in a PKCS12 keystore and users can extract results via a `txt` file for later use.

## Getting started

1. Add your keystore file into `src/main/resources` directory.
2. Setup keystore file configuration in `src/main/resources/application.conf` using the above file name and respective 
password of the keystore.
3. Run `sbt clean compile run` and output will be display on terminal and store in the `result.txt` file.

## Support
Any questions or suggestions?
Feel free to contact me through *dushnz@gmail.com*
