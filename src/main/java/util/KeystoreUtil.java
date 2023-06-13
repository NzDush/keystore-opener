package util;

import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class KeystoreUtil {

    public static KeyStore getKeyStoreInstance(File keyStoreFile, String keyStorePassword) throws IOException,
            KeyStoreException,
            CertificateException,
            NoSuchAlgorithmException,
            NoSuchProviderException {
        Security.addProvider(new BouncyCastleProvider());
        FileInputStream fileInputStream = new FileInputStream(keyStoreFile);
        char[] keyStorePasswordAsCharArray = keyStorePassword.toCharArray();

        KeyStore keystore = KeyStore.getInstance("PKCS12", "BC");
        keystore.load(fileInputStream, keyStorePasswordAsCharArray);

        return keystore;
    }

    public static List<String> getAliases(KeyStore inputKeyStore) throws KeyStoreException {
        Enumeration enumeration = inputKeyStore.aliases();
        List<String> aliases = new ArrayList<>();

        while (enumeration.hasMoreElements()) {
            String alias = (String) enumeration.nextElement();
            aliases.add(alias);
        }

        return aliases;
    }

    public static X509Certificate getCertificate(KeyStore inputKeyStore, String alias) throws KeyStoreException {
        Certificate cert = inputKeyStore.getCertificate(alias);
        return (X509Certificate) cert;
    }

    public static String getFingerprintSHA1(X509Certificate inputCertificate) throws CertificateEncodingException {
        byte[] encodedCert = inputCertificate.getEncoded();
        return DigestUtils.sha1Hex(encodedCert);
    }

    public static String getFingerprintSHA256(X509Certificate inputCertificate) throws CertificateEncodingException {
        byte[] encodedCert = inputCertificate.getEncoded();
        return DigestUtils.sha256Hex(encodedCert);
    }

}
