/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flatmatesrest;

/**
 *
 * @author Karol
 */
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class SSLUtils {
    public static void trustEveryone() {
        try {
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new X509TrustManager[]{new X509TrustManager(){
                public void checkClientTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {}
                public void checkServerTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {}
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }}}, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(
                    context.getSocketFactory());
        } catch (Exception e) { // should never happen
            e.printStackTrace();
        }
    }
    public static void trustEveryone2(){
    
    // Create a trust manager that does not validate certificate chains
TrustManager[] trustAllCerts = new TrustManager[] { 
    new X509TrustManager() {     
        public java.security.cert.X509Certificate[] getAcceptedIssuers() { 
            return new X509Certificate[0];
        } 
        public void checkClientTrusted( 
            java.security.cert.X509Certificate[] certs, String authType) {
            } 
        public void checkServerTrusted( 
            java.security.cert.X509Certificate[] certs, String authType) {
        }
    } 
}; 

// Install the all-trusting trust manager
try {
    SSLContext sc = SSLContext.getInstance("SSL"); 
    sc.init(null, trustAllCerts, new java.security.SecureRandom()); 
    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
} catch (GeneralSecurityException e) {
} 
    }
}

