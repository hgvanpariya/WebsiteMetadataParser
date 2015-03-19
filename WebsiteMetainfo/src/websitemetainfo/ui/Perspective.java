package websitemetainfo.ui;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import websitemetainfo.views.ParseWebsiteView;

public class Perspective implements IPerspectiveFactory {

	/**
	 * The ID of the perspective as specified in the extension.
	 */
	public static final String ID = "WebsiteMetainfo.perspective";

	public void createInitialLayout(IPageLayout layout) {
		String editorArea = layout.getEditorArea();
		layout.setEditorAreaVisible(false);
		
		layout.addStandaloneView(ParseWebsiteView.ID,  false, IPageLayout.LEFT, 1f, editorArea);
//		IFolderLayout folder = layout.createFolder("messages", IPageLayout.TOP, 0.5f, editorArea);
//		folder.addPlaceholder(ParseWebsiteView.ID + ":*");
//		folder.addView(ParseWebsiteView.ID);
		
		layout.getViewLayout(ParseWebsiteView.ID).setCloseable(false);
	}
}
