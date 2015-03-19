package websitemetainfo.views;


import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.wb.swt.SWTResourceManager;

import websitemetainfo.util.Parser;



public class ParseWebsiteView extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "websitemetainfo.views.ParseWebsiteView";

	private Text txtWebsitename;
	private Text txtTitle;
	private Text txtDescription;
	private List keywordList ;
	Group grpMetadata;

	
	 
	/**
	 * The constructor.
	 */
	public ParseWebsiteView() {
	}

	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent) {
		
		ScrolledComposite scrolledComposite = new ScrolledComposite(parent, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		
		Composite composite = new Composite(scrolledComposite, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));
		
		Group grpWebsiteInfo = new Group(composite, SWT.NONE);
		grpWebsiteInfo.setLayout(new GridLayout(1, false));
		grpWebsiteInfo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		grpWebsiteInfo.setText("Website Info");
		
		Composite web_url_composite = new Composite(grpWebsiteInfo, SWT.NONE);
		web_url_composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		web_url_composite.setLayout(new GridLayout(2, false));
		
		Label lblEnterWebsiteName = new Label(web_url_composite, SWT.NONE);
		lblEnterWebsiteName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblEnterWebsiteName.setText("Enter Website URL");
		
		txtWebsitename = new Text(web_url_composite, SWT.BORDER);
		txtWebsitename.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(web_url_composite, SWT.NONE);
		
		Label lblPleaseAddProtocol = new Label(web_url_composite, SWT.NONE);
		lblPleaseAddProtocol.setText("Please add protocol information as well. E.g. http://www.google.com");
		
		final Label lblInvalidUrl = new Label(grpWebsiteInfo, SWT.NONE);
		lblInvalidUrl.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lblInvalidUrl.setText("Invalid URL");
		lblInvalidUrl.setVisible(false);
		
		Composite submitButtonComposite = new Composite(grpWebsiteInfo, SWT.NONE);
		submitButtonComposite.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		submitButtonComposite.setLayout(new GridLayout(1, false));
		
		Button btnSubmit = new Button(submitButtonComposite, SWT.NONE);
		btnSubmit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Parser parser = new Parser();

				String url = txtWebsitename.getText();
				StringBuffer getContent;
				try {
					getContent = parser.parseWebsite(url);
					String titleTag = parser.getTitleTag(getContent);
					String metaDesc = parser.getMetaDiscription(getContent);
					String[] keywords = parser.getKeywords(getContent);
					txtTitle.setText(titleTag);
					txtDescription.setText(metaDesc);
					keywordList.removeAll();
					if(keywords!=null){
						keywordList.setItems(keywords);
					}
					grpMetadata.setText("Metada for "+url);
					lblInvalidUrl.setVisible(false);
				} catch (IOException ek) {
					lblInvalidUrl.setVisible(true);
				}
			}
		});
		btnSubmit.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		btnSubmit.setText("Submit");
		new Label(composite, SWT.NONE);
		
		grpMetadata = new Group(composite, SWT.NONE);
		grpMetadata.setLayout(new GridLayout(2, false));
		grpMetadata.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		grpMetadata.setText("Metadata");
		
		Label lblTitle = new Label(grpMetadata, SWT.NONE);
		lblTitle.setText("Title");
		
		txtTitle = new Text(grpMetadata, SWT.BORDER | SWT.WRAP | SWT.MULTI);
		txtTitle.setEditable(false);
		GridData gd_txtTitle = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_txtTitle.heightHint = 43;
		txtTitle.setLayoutData(gd_txtTitle);
		
		Label lblDescription = new Label(grpMetadata, SWT.NONE);
		GridData gd_lblDescription = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblDescription.widthHint = 137;
		lblDescription.setLayoutData(gd_lblDescription);
		lblDescription.setText("Description");
		
		txtDescription = new Text(grpMetadata, SWT.BORDER | SWT.WRAP | SWT.MULTI);
		txtDescription.setEditable(false);
		GridData gd_txtDescription = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_txtDescription.heightHint = 54;
		txtDescription.setLayoutData(gd_txtDescription);
		
		Label lblKeywords = new Label(grpMetadata, SWT.NONE);
		lblKeywords.setText("Keywords");
		
		keywordList = new List(grpMetadata, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
		keywordList.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		scrolledComposite.setContent(composite);
		scrolledComposite.setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
	}

	
	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
	}
}