import org.jsoup.select.Elements;

public class UrlComponent {
	
	private String sourceURL;
    private Elements links;
	private Elements media;

    public UrlComponent(String sourceURL, Elements links/*, Elements media, Elements imports*/) {
    	this.sourceURL = sourceURL;
		this.links = links;
		//this.media = media;
		//this.imports = imports;
	}
    
    public String getSourceURL() {
		return sourceURL;
	}
	public void setSourceURL(String sourceURL) {
		this.sourceURL = sourceURL;
	}
	public Elements getLinks() {
		return links;
	}
	public void setLinks(Elements links) {
		this.links = links;
	}
	public Elements getMedia() {
		return media;
	}
	public void setMedia(Elements media) {
		this.media = media;
	}
	public Elements getImports() {
		return imports;
	}
	public void setImports(Elements imports) {
		this.imports = imports;
	}
	private Elements imports;


}
