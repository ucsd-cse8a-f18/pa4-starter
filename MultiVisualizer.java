// To test, before running, do "python -m SimpleHTTPServer" from this directory
// URL params don't work from file:/// urls; this is an easy way to start a
// local server


// Browser opening code courtesy of
// https://stackoverflow.com/a/17950164/2718315
import java.awt.Desktop;
import java.net.URI;


List<String> getColumn(String csv, int col) {
  String[] lines = csv.split("\n");
  List<String> elements = new ArrayList<>();
  for(int i = 1; i < lines.length; i += 1) {
    elements.add(lines[i].split(",")[col]);
  }
  return elements;
}

String getJSONCol(String csv, String colname) {
  int col = indexForColumn(csv, colname);
  String elts = String.join(",", getColumn(csv, col));
  return elts;
}

String getJSONStringCol(String csv, String colname) {
  int col = indexForColumn(csv, colname);
  String[] lines = csv.split("\n");
  List<String> elts = new ArrayList<>();
  for(int i = 1; i < lines.length; i += 1) {
    elts.add("\""+lines[i].split(",")[col]+"\"");
  }
  return String.join(",", elts);
}

boolean drawGeoTable(String toDraw, int zoom){
  try {
    URI url = new URI(
    "https",
    "cseweb.ucsd.edu",
    "/classes/fa18/cse8a/maps-table.html",
      "lats=" + getJSONCol(toDraw,"lat") + "&" +
      "lons=" + getJSONCol(toDraw,"long") + "&" +
      "labels=" + getJSONCol(toDraw,"event_id") + "&" +
      "zoom=" + zoom + "&",
      null);

      System.out.println("URL is" +url.toString());

      if (Desktop.isDesktopSupported()) {
        Desktop.getDesktop().browse(url);
      } else {
        System.out.println("Your system may not be supported, try running on the lab machines, or copy/paste this URL into your browser: \n" + url);
      }
    }
    catch(Exception e){System.out.println("Malformed URI");}

  return true;
}
