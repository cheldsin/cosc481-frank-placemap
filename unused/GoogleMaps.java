
//import ApiInterface;

public class GoogleMaps { // implements ApiInterface {


}


// http://code.google.com/apis/maps/documentation/webservices/index.html#JavaSignatureExample
class UrlSigner {

  // Note: Generally, you should store your private key someplace safe
  // and read them into your code

  private static String keyString = "YOUR_PRIVATE_KEY";
  
  // The URL shown in these examples must be already
  // URL-encoded. In practice, you will likely have code
  // which assembles your URL from user or web service input
  // and plugs those values into its parameters.
  private static String urlString = "YOUR_URL_TO_SIGN";

  // This variable stores the binary key, which is computed from the string (Base64) key
  private static byte[] key;
  
  public static void main(String[] args) throws IOException,
    InvalidKeyException, NoSuchAlgorithmException, URISyntaxException {
    
    // Convert the string to a URL so we can parse it
    URL url = new URL(urlString);
    
    UrlSigner signer = new UrlSigner(keyString);
    String request = signer.signRequest(url.getPath(),url.getQuery());
    
    System.out.println("Signed URL :" + url.getProtocol() + "://" + url.getHost() + request);
  }
  
  public UrlSigner(String keyString) throws IOException {
    // Convert the key from 'web safe' base 64 to binary
    keyString = keyString.replace('-', '+');
    keyString = keyString.replace('_', '/');
    System.out.println("Key: " + keyString);
    this.key = Base64.decode(keyString);
  }

  public String signRequest(String path, String query) throws NoSuchAlgorithmException,
    InvalidKeyException, UnsupportedEncodingException, URISyntaxException {
    
    // Retrieve the proper URL components to sign
    String resource = path + '?' + query;
    
    // Get an HMAC-SHA1 signing key from the raw key bytes
    SecretKeySpec sha1Key = new SecretKeySpec(key, "HmacSHA1");

    // Get an HMAC-SHA1 Mac instance and initialize it with the HMAC-SHA1 key
    Mac mac = Mac.getInstance("HmacSHA1");
    mac.init(sha1Key);

    // compute the binary signature for the request
    byte[] sigBytes = mac.doFinal(resource.getBytes());

    // base 64 encode the binary signature
    String signature = Base64.encodeBytes(sigBytes);
    
    // convert the signature to 'web safe' base 64
    signature = signature.replace('+', '-');
    signature = signature.replace('/', '_');
    
    return resource + "&signature=" + signature;
  }
}

