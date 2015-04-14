import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class DigestCalculator2 {
 /**
 * @param args
 */
 public static void main(String[] args) {
 if (args.length < 2) {
 System.err.println("Usage: java DigestCalculator2 \"Caminho_Arq1\"\"Caminho_Arq2\" ... \"Caminho_ArqN\" \"Caminho_ArqListaDigest\"");
 System.exit(1);
 }
 List<String> argList = Arrays.asList(args);
 List<String> files = argList.subList(0, argList.size()-1);
 String digestsFilePath = argList.get(argList.size()-1);
 // check digestfile path
 File file = new File(digestsFilePath);
 if(!file.isFile()) {
 System.err.println("\"Caminho_ArqListaDigest\" is not a valid file path.");
 System.exit(1);
 }
 // check all files path
 int i = 0;
 for(String filePath : files) {
 i++;
 file = new File(filePath);
 if(!file.isFile()) {
 System.err.println(filePath+" (\"Caminho_Arq"+i+"\") is not a valid file path.");
 System.exit(1);
 }
 }
 // build the map for verification
 Map<String, Map<String, String>> digestMap = buildMap(digestsFilePath);
 // check all files
 for(String filePath : files) {
 try {
 byte[] fileBytes = getFileBytes(new File(filePath)); 
 List<String> digestTypes = Arrays.asList("SHA1", "MD5");
 byte[] digest;
 String digestHex;
 String fileName;
 // check both digest types
 for(String digestType : digestTypes ) {
 MessageDigest messageDigest = MessageDigest.getInstance(digestType);
 messageDigest.update(fileBytes);
 digest = messageDigest.digest();
 digestHex = convertToHex(digest);
 fileName = getFileName(filePath);
 // digest check and output
 if(digestMap.containsKey(fileName)) {
 Map<String, String> digestTypeMap = digestMap.get(fileName);
 if(digestTypeMap.containsKey(digestType)) {
 if(digestTypeMap.get(digestType).equals(digestHex)) {
 System.out.println(fileName +" "+ digestType +" "+
digestHex +" (OK)");
 } else {
 System.out.println(fileName +" "+ digestType +" "+
digestHex +" (NOT OK)");
 }
 } else {
 System.out.println(fileName +" "+ digestType +" "+ digestHex
+" (NOT FOUND)");
 appendDigestToFileBottom(digestsFilePath, fileName,
digestType, digestHex);
 }
 } else {
 System.out.println(fileName +" "+ digestType +" "+ digestHex
+" (NOT FOUND)");
 appendDigestToFileBottom(digestsFilePath, fileName, digestType,
digestHex);
 }
 }
 } catch (IOException e) {
 e.printStackTrace();
 } catch (NoSuchAlgorithmException e) {
 e.printStackTrace();
 } catch (Exception e) {
 e.printStackTrace();
 }
 }
 }
 private static void appendDigestToFileBottom(String digestsFilePath, String fileName,
String digestType, String digestHex) {
 try {
 FileWriter fw = new FileWriter(digestsFilePath, true);
 BufferedWriter out = new BufferedWriter(fw);
 out.write(fileName +" "+ digestType +" "+ digestHex +"\n");
 out.close();
 } catch(IOException e) {
 e.printStackTrace(); }
 }
 private static byte[] getFileBytes(File file) throws IOException {
 // check file
 if (!file.exists()) {
 return null;
 }
 // verify file size
 long length = file.length();
 int maxLength = Integer.MAX_VALUE;
 if (length > maxLength)
 throw new IOException(String.format("The file %s is too large",
file.getName()));
 int len = (int) length;
 byte[] bytes = new byte[len];
 InputStream in = new FileInputStream(file);
 // read bytes
 int offset = 0, n = 0;
 while (offset < len && n >= 0) {
 n = in.read(bytes, offset, len - offset);
 offset += n;
 }
 if (offset < len)
 throw new IOException("Failed to read the full content from: " +
file.getName());
 in.close();
 return bytes;
 }
 private static String getFileName(String filePath) {
 String[] fileParts = filePath.split("\\\\");
 String fileName = fileParts[fileParts.length-1];
 return fileName;
 }
 private static String convertToHex(byte[] byteSequence) {
 StringBuffer buffer = new StringBuffer();
 for (int i = 0; i < byteSequence.length; i++) {
 String hex = Integer.toHexString(0x0100 + (byteSequence[i] &
0x00FF)).substring(1);
 buffer.append((hex.length() < 2 ? "0" : "") + hex);
 }
 return buffer.toString();
 }
 private static Map<String, Map<String, String>> buildMap(String fileName) {
 try {
 BufferedReader in = new BufferedReader(new FileReader(fileName));
 Map<String, Map<String, String>> digestMap = new HashMap<String,
Map<String,String>>();
 String buffer; while ((buffer = in.readLine()) != null) {
 String[] line = buffer.split(" ");
 Map<String, String> digestTypeMap;
 if(digestMap.containsKey(line[0])) {
 digestTypeMap = digestMap.get(line[0]);
 } else {
 digestTypeMap = new HashMap<String, String>();
 }
 digestTypeMap.put(line[1], line[2]);
 digestMap.put(line[0], digestTypeMap);
 }
 in.close();
 return digestMap;
 } catch (IOException e) {
 e.printStackTrace();
 return null;
 }
 }
}