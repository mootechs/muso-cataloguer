import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;
import java.util.ArrayList;
import java.io.*;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import com.sun.xml.txw2.output.IndentingXMLStreamWriter;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.wb.swt.SWTResourceManager;


//WARNING: IndentingXMLStreamWriter REQUIRES DEPENDENCY INSTALLATION VIA MAVEN 
/*
 * 
<dependency>
    <groupId>org.glassfish.jaxb</groupId>
    <artifactId>txw2</artifactId>
    <version>2.2.11</version>
</dependency>
 */


public class cataloguer_swt {

	protected Shell shlMusoCatologuer;
	
	//Variables I created
	//FIXME: Maybe in the future switch to getters/setters instead of using static members
	//FIXME: Validate fields 
	//FIXME: Add  <rdfs:seeAlso rdf:resource="">
	//https://stackoverflow.com/questions/13586772/selected-color-of-prompt-message-in-empty-text-box
	//https://stackoverflow.com/questions/22167743/swt-display-popup-on-treeitem-mousehover
	//https://www.w3.org/RDF/Validator/
	public static String filepath;
	public static String title;
	public static String author;
	public static String date;
	public static String archive;
	public static String type;
	public static String role;
	public static String discipline;
	public static String federation;
	public static String[] roles;
	public static Text newEditor;
	public static ArrayList<Integer> indeces;
	public static ArrayList<String> tag_list;
	public static ArrayList<String> name_list;
	
	//MUSO 
	public static String created_label;
	public static String created_value;
	public static String subgenre;
	public static String autograph;
	public static String rism_ref;
	public static String loc_title;
	public static String license;
	public static String notation;
	public static String other_id;
	
	//MUSO Lists
	public static String[] genres;
	public static String[] disciplines;
	public static String[] notations;


	
	private Group grpMusoStandards;
	private Label lblLicense;
	private Group grpArcStandards; 
	private Label label;
	private Label label_2;
	private Text text;
	private Text text_2;
	private Label label_3;
	private Text text_3;
	private Text text_4;
	private Label label_4;
	private Label label_5;
	private Label lblFederation;
	private Text text_8;
	private static List genre_list;
	private Label label_9;
	private Label label_10;
	private static List discipline_list;
	private Label lblCreated;
	private Text text_10;
	private Label lblNewLabel;
	private Text text_11;
	private Label lblAutograph;
	private Text text_6;
	private Label lblNotation;
	private static List notation_list;
	private Label lblNewLabel_1;
	private Text text_7;
	private Label lblNewLabel_2;
	private Text text_9;
	private Text text_12;
	private Table table;
	private TableColumn tblclmnNewColumn;
	private TableColumn tblclmnNewColumn_1;
	private static List license_list;
	private Label lblDataAndDigital;
	private Group group;
	private Text text_13;
	private Label label_6;
	private Label lblNewLabel_3;


	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			cataloguer_swt window = new cataloguer_swt();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlMusoCatologuer.open();
		shlMusoCatologuer.layout();
		while (!shlMusoCatologuer.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	/** Prints out the fields of the form 
	 * 
	 */ 
	public void print_fields() {
		System.out.format("Title: "        + title +
						  "\nAuthor:"      + author +
						  "\nDate:"        + date +
						  "\narchive:"     + archive +
						  "\ntype:"        + type +
						  "\nrole:"        + role +
						  "\ndiscipline:"  + discipline + 
						  "\nfederation:"  + federation);
	}
	
	/** Opens save dialog window 
	 * 
	 */
	public void saveXML() {
		final Shell shell = new Shell(Display.getCurrent());
        new Thread(new Runnable() {
            public void run() {
                try { Thread.sleep(1000); }
                catch(Exception e) { }
	                Display.getDefault().syncExec(new Runnable() {
					public void run() {
						FileDialog fileSave = new FileDialog(shell, SWT.SAVE);
		                fileSave.setFilterNames(new String[] { "XML", "RDF", "All Files (*.*)" });
		                fileSave.setFilterExtensions(new String[] { "*.xml", "*.rdf", "*.*" });
		                fileSave.setFilterPath("C:\\"); // Windows path
		                fileSave.setFileName("");
		                String open = fileSave.open();
		                if (open != null) {
		                	try {
								generateXML(open);
								MessageBox box = new MessageBox(Display.getDefault().getActiveShell(), SWT.OK);
								box.setText("Process Complete");
								box.setMessage("File Generated Successfully!");
								box.open(); 
							} catch (XMLStreamException | IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
		                }
		                else {
	                		System.out.println("Save cancelled\n");
		                } //else open == null
					} //inner run
				});  //display sync  
	        }  //outer run
        }).start();  //start runnable in new thread
		
	}
	
	//Let's map the roles to our XML tags

	public void getTableEntries() {
		TableItem[] tableItems = table.getItems();
		String[] tags = {"role:ART", "role:AUT", "role:EDT", "role:PBL", "role:TRL ", "role:CRE", "role:ETR", "role:EGR", "role:OWN", "role:ART", "role:ARC", "role:BND", "role:BKD", "role:BKP", "role:CLL", "role:CTG", "role:COL", "role:CLR ", "role:CWT", "role:COM", "role:CMT", "role:CRE", "role:DUB", "role:FAC", "role:ILU", "role:ILL", "role:LTG", "role:PRT", "role:POP", "role:PRM", "role:RPS", "role:RBR", "role:SCR", "role:SCL", "role:TYD", "role:TYG ", "role:WDE", "role:WDC"};
		name_list = new ArrayList<String>();
		indeces = new ArrayList<Integer>();
		tag_list = new ArrayList<String>();
		int i = 0;
		//Find out which items are not null
		for (i = 0; i < tableItems.length; ++i) {
			//Go through each row in column 1
			String item = tableItems[i].getText(1);
			//there is an item in that row, so store the index of that item
			if (item != "") {
				indeces.add(i);
				name_list.add(item);
			}
		}
		for (i = 0; i < indeces.size(); ++i) {
			int index = indeces.get(i);
			tag_list.add(tags[index]);
		}
	}
	/**
	 * XML parser
	 * https://stackoverflow.com/questions/5059224/which-is-the-best-library-for-xml-parsing-in-java
	 * https://www.tutorialspoint.com/java_xml/java_dom_parse_document.htm
	 * https://stackoverflow.com/questions/23520208/how-to-create-xml-file-with-specific-structure-in-java
	 * https://stackoverflow.com/questions/8449316/how-to-create-attribute-xmlnsxsd-to-xml-node-in-java
	 * https://docs.oracle.com/javase/6/docs/api/javax/xml/stream/XMLStreamWriter.html
	 * https://stackoverflow.com/questions/9164893/how-do-i-add-a-maven-dependency-in-eclipse
	 * https://stackoverflow.com/questions/10105187/java-indentingxmlstreamwriter-alternative
	 * http://discover.library.ucdavis.edu:8080/TripleDataProcessor/webapi/search
	 */

	public static void generateXML(String filename) throws XMLStreamException, IOException {
			//String filename = "test.xml";
	        XMLOutputFactory xmlOutFact = XMLOutputFactory.newInstance();
	        XMLStreamWriter writer =  new IndentingXMLStreamWriter(						//REQUIRES DEPENDENCY SEE WARNING ABOVE
	        								xmlOutFact.createXMLStreamWriter(
	        								new FileOutputStream(filename), "UTF-8")); 
	        
	        //Start Document
	        writer.writeStartDocument("UTF-8", "1.0");
	        writer.writeStartElement("rdf:RDF");
	        
	        //Setup name spaces
			writer.writeNamespace("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
			writer.writeNamespace("dc", "http://purl.org/dc/elements/1.1/");
			writer.writeNamespace("dcterms", "http://purl.org/dc/terms/");
			writer.writeNamespace("collex", "http://www.collex.org/schema#");
			writer.writeNamespace("ra", "http://www.rossettiarchive.org/schema#");
			writer.writeNamespace("rdfs", "http://www.w3.org/2000/01/rdf-schema#");
			writer.writeNamespace("role", "http://www.loc.gov/loc.terms/relators/");
			
			//Custom name space
			writer.writeStartElement("rdf:MUSO");
			writer.writeAttribute("rdf", "", "about", "https://muso.arts.gla.ac.uk/");
			writer.writeCharacters(System.getProperty("line.separator"));
			
			/* ADD IN ELEMENTS */
			if (title != null) {
		        writer.writeStartElement("dc:title");
		        writer.writeCharacters(title);
				writer.writeEndElement();
				writer.writeCharacters(System.getProperty("line.separator"));
			}
			
			
			//federation
			if (federation != null) {
		        writer.writeStartElement("collex:federation");
		        writer.writeCharacters(federation);
				writer.writeEndElement();
				writer.writeCharacters(System.getProperty("line.separator"));
			}
			
			//archive
			if (archive != null) {
		        writer.writeStartElement("collex:archive");
		        writer.writeCharacters(archive);
				writer.writeEndElement();
				writer.writeCharacters(System.getProperty("line.separator"));
			}
			
			//type
			if (type != null) {
		        writer.writeStartElement("dc:type");
		        writer.writeCharacters(type);
				writer.writeEndElement();
				writer.writeCharacters(System.getProperty("line.separator"));
			}
			
			//Now print the lists out
			//Genres
			if (genres != null) {
				for (int i = 0; i < genres.length; ++i) {
			        writer.writeStartElement("collex:genre");
			        writer.writeCharacters(genres[i].trim());
					writer.writeEndElement();
				}
				writer.writeCharacters(System.getProperty("line.separator"));
			}
			
			//Roles and Names
			if (name_list != null) {
				for (int i = 0; i < name_list.size(); ++i) {
			        writer.writeStartElement(tag_list.get(i).trim());
			        writer.writeCharacters(name_list.get(i).trim());
					writer.writeEndElement();
				}
				writer.writeCharacters(System.getProperty("line.separator"));
			}
			
			//Disciplines
			if (disciplines != null) {
				for (int i = 0; i < disciplines.length; ++i) {
			        writer.writeStartElement("collex:discipline");
			        writer.writeCharacters(disciplines[i].trim());
					writer.writeEndElement();
				}
				writer.writeCharacters(System.getProperty("line.separator"));
			}
			//Notations
			if (notations != null) {
				for (int i = 0; i < notations.length; ++i) {
			        writer.writeStartElement("collex:genre");
			        writer.writeCharacters(notations[i].trim());
					writer.writeEndElement();
				}
				writer.writeCharacters(System.getProperty("line.separator"));
			}
			//MUSO tags
			//Remember these are optional so they can be null
			if (created_label != null && created_value != null) {
		        writer.writeStartElement("muso:created");
		        writer.writeStartElement("rdfs:label");
		        writer.writeCharacters(created_label);
				writer.writeEndElement();
		        writer.writeStartElement("rdf:value");
		        writer.writeCharacters(created_value);
				writer.writeEndElement();
				writer.writeEndElement();
				writer.writeCharacters(System.getProperty("line.separator"));
			}
			if (subgenre != null) {
		        writer.writeStartElement("muso:subgenre");		        
		        writer.writeCharacters(subgenre);
				writer.writeEndElement();
				writer.writeCharacters(System.getProperty("line.separator"));
			}
			if (autograph != null) {
		        writer.writeStartElement("muso:autograph");
		        writer.writeCharacters(autograph);
				writer.writeEndElement();
				writer.writeCharacters(System.getProperty("line.separator"));
			}
			if (rism_ref != null) {
		        writer.writeStartElement("muso:rism");
		        writer.writeCharacters(rism_ref);
				writer.writeEndElement();
				writer.writeCharacters(System.getProperty("line.separator"));
			}
			if (loc_title != null) {
		        writer.writeStartElement("muso:uniform_title");
		        writer.writeCharacters(loc_title);
				writer.writeEndElement();
				writer.writeCharacters(System.getProperty("line.separator"));
			}
			/*//This gives an error
			if (other_id_label != null && other_id_value != null) {
				writer.writeAttribute("muso", "", "other_id", other_id_label);
				writer.writeCharacters(other_id_value);
				writer.writeCharacters(System.getProperty("line.separator"));
			}
			*/
			if (other_id != null) {
				writer.writeStartElement("muso:other_id");
				writer.writeCharacters(other_id);
				writer.writeEndElement();
			}
			if (license != null) {
		        writer.writeStartElement("muso:license");
		        writer.writeCharacters(license);
				writer.writeEndElement();
				writer.writeCharacters(System.getProperty("line.separator"));
			}


			//End Elements
			//writer.writeEndElement();
			writer.writeEndElement();		//Closes <rdf:RDF>
			writer.writeCharacters(System.getProperty("line.separator"));
			writer.writeEndDocument();
			writer.close();
			
	}
	
	
	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		//Window resizing is disabled for now
		shlMusoCatologuer = new Shell(SWT.CLOSE | SWT.TITLE | SWT.MIN);
		shlMusoCatologuer.setSize(740, 850);
		shlMusoCatologuer.setText("MUSO Catologuer");
		shlMusoCatologuer.setLayout(new FormLayout());
		
		Button btnRun = new Button(shlMusoCatologuer, SWT.NONE);
		btnRun.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
					//Get the selections from the lists
					genres = genre_list.getSelection();
					disciplines = discipline_list.getSelection();
					notations = notation_list.getSelection();
					if (license_list.getSelectionCount() != 0) {
						license = license_list.getItem(license_list.getSelectionIndex());
					}
					getTableEntries();
					saveXML();
					//generateXML();
					//System.out.println("XML Written!");
				//print_fields();
			}
		});
		FormData fd_btnRun = new FormData();
		fd_btnRun.bottom = new FormAttachment(100, -10);
		fd_btnRun.right = new FormAttachment(100, -10);
		btnRun.setLayoutData(fd_btnRun);
		btnRun.setText("Run");
		
		grpMusoStandards = new Group(shlMusoCatologuer, SWT.NONE);
		grpMusoStandards.setText("MuSO Standards");
		FormData fd_grpMusoStandards = new FormData();
		fd_grpMusoStandards.bottom = new FormAttachment(btnRun, -6);
		fd_grpMusoStandards.left = new FormAttachment(0, 10);
		grpMusoStandards.setLayoutData(fd_grpMusoStandards);
		
		lblLicense = new Label(grpMusoStandards, SWT.NONE);
		lblLicense.setBounds(353, 27, 70, 20);
		lblLicense.setText("License:");
		
		license_list = new List(grpMusoStandards, SWT.BORDER|SWT.SINGLE|SWT.V_SCROLL);
		license_list.setItems(new String[] {"cc-by-nc", "cc-by-nc-nd", "cc-by-nc-sa", "cc-by-nd", "copyright", "patent", "trademark"});
		license_list.setBounds(450, 23, 215, 105);
		
		grpArcStandards = new Group(shlMusoCatologuer, SWT.NONE);
		fd_grpMusoStandards.right = new FormAttachment(grpArcStandards, 0, SWT.RIGHT);
		fd_grpMusoStandards.top = new FormAttachment(grpArcStandards, 6);
		grpArcStandards.setText("ARC Schema");
		FormData fd_grpArcStandards = new FormData();
		fd_grpArcStandards.bottom = new FormAttachment(100, -330);
		fd_grpArcStandards.left = new FormAttachment(0, 10);
		
		lblCreated = new Label(grpMusoStandards, SWT.NONE);
		lblCreated.setBounds(10, 195, 70, 20);
		lblCreated.setText("Created:");
		
		text_10 = new Text(grpMusoStandards, SWT.BORDER);
		text_10.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				loc_title = text_10.getText().trim();
			}
		});
		text_10.setBounds(97, 24, 217, 26);
		
		lblNewLabel = new Label(grpMusoStandards, SWT.NONE);
		lblNewLabel.setBounds(10, 70, 70, 20);
		lblNewLabel.setText("Subgenre:");
		
		text_11 = new Text(grpMusoStandards, SWT.BORDER);
		text_11.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				subgenre = text_11.getText().trim();
			}
		});
		text_11.setBounds(97, 67, 217, 26);
		
		lblAutograph = new Label(grpMusoStandards, SWT.NONE);
		lblAutograph.setBounds(9, 111, 82, 20);
		lblAutograph.setText("Autograph:");
		
		text_6 = new Text(grpMusoStandards, SWT.BORDER);
		text_6.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				autograph = text_6.getText().trim();
			}
		});
		text_6.setBounds(97, 108, 217, 26);
		
		lblNotation = new Label(grpMusoStandards, SWT.NONE);
		lblNotation.setBounds(353, 153, 70, 20);
		lblNotation.setText("Notation:");
		
		notation_list = new List(grpMusoStandards, SWT.BORDER|SWT.MULTI|SWT.V_SCROLL);
		notation_list.setItems(new String[] {"graphic", "letter", "mensural", "solmization", "staff", "tablature", "tonic sol-fa", "neumatic", "encoded"});
		notation_list.setBounds(450, 149, 215, 105);
		
		lblNewLabel_1 = new Label(grpMusoStandards, SWT.NONE);
		lblNewLabel_1.setBounds(10, 153, 70, 20);
		lblNewLabel_1.setText("RISM Ref:");
		
		text_7 = new Text(grpMusoStandards, SWT.BORDER);
		text_7.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				rism_ref = text_7.getText().trim();
			}
		});
		text_7.setBounds(97, 150, 217, 26);
		
		lblNewLabel_2 = new Label(grpMusoStandards, SWT.NONE);
		lblNewLabel_2.setText("LOC Title:");
		lblNewLabel_2.setBounds(10, 27, 82, 20);
		
		text_9 = new Text(grpMusoStandards, SWT.BORDER);
		text_9.setMessage("Label");
		text_9.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				created_label = text_9.getText().trim();
			}
		});
		text_9.setBounds(97, 192, 104, 26);
		grpArcStandards.setLayoutData(fd_grpArcStandards);
		
		label = new Label(grpArcStandards, SWT.NONE);
		label.setText("Title:");
		label.setBounds(10, 26, 32, 20);
		
		label_2 = new Label(grpArcStandards, SWT.NONE);
		label_2.setText("Date:");
		label_2.setBounds(10, 69, 35, 20);
		
		text = new Text(grpArcStandards, SWT.BORDER);
		text.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				title = text.getText().trim();
			}
		});
		text.setBounds(97, 23, 217, 26);
		
		text_2 = new Text(grpArcStandards, SWT.BORDER);
		text_2.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				date = text_2.getText().trim();
			}
		});
		text_2.setBounds(97, 66, 217, 26);
		
		label_3 = new Label(grpArcStandards, SWT.NONE);
		label_3.setText("Archive:");
		label_3.setBounds(10, 108, 52, 20);
		
		text_3 = new Text(grpArcStandards, SWT.BORDER);
		text_3.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				archive = text_3.getText().trim();
			}
		});
		text_3.setBounds(97, 105, 217, 26);
		
		text_4 = new Text(grpArcStandards, SWT.BORDER);
		text_4.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				type = text_4.getText().trim();
			}
		});
		text_4.setBounds(97, 147, 217, 26);
		
		label_4 = new Label(grpArcStandards, SWT.NONE);
		label_4.setText("Type:");
		label_4.setBounds(10, 150, 34, 20);
		
		label_5 = new Label(grpArcStandards, SWT.NONE);
		label_5.setText("Role:");
		label_5.setBounds(351, 160, 33, 20);
		
		lblFederation = new Label(grpArcStandards, SWT.NONE);
		lblFederation.setText("Federation:");
		lblFederation.setBounds(10, 189, 74, 20);
		
		text_8 = new Text(grpArcStandards, SWT.BORDER);
		text_8.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				federation = text_8.getText().trim();
			}
		});
		text_8.setBounds(97, 186, 217, 26);
		
		genre_list = new List(grpArcStandards, SWT.BORDER|SWT.MULTI|SWT.V_SCROLL);
		genre_list.setItems(new String[] {"Advertisement \t", "Animation \t", "Bibliography \t", "Catalog", "Chronology \t", "Citation \t", "Collection \t", "Correspondence", "Criticism \t", "Drama \t", "Ephemera \t", "Essay", "Fiction \t", "Film, Documentary \t", "Film, Experimental \t", "Film, Narrative", "Film, Other \t", "Historiography \t", "Interview \t", "Life Writing", "Liturgy \t", "Musical Analysis \t", "Music, Other \t", "Musical Work", "Musical Score \t", "Nonfiction \t", "Paratext \t", "Performance", "Philosophy \t", "Photograph \t", "Political Statement \t", "Poetry", "Religion \t", "Reference Works \t", "Review \t", "Scripture", "Sermon \t", "Speech \t", "Translation \t", "Travel Writing", "Unspecified \t", "Visual Art"});
		genre_list.setBounds(446, 26, 217, 105);
		
		label_9 = new Label(grpArcStandards, SWT.NONE);
		label_9.setText("Genre:");
		label_9.setBounds(351, 26, 42, 20);
		
		label_10 = new Label(grpArcStandards, SWT.NONE);
		label_10.setText("Discipline:");
		label_10.setBounds(10, 235, 68, 20);
		
		discipline_list = new List(grpArcStandards, SWT.BORDER|SWT.MULTI|SWT.V_SCROLL);
		discipline_list.setItems(new String[] {"Anthropology \t", "Archaeology \t", "Architecture \t", "Art History \t", "Art Studies", "Book History \t", "Classics, Ancient History \t", "Dance Studies \t", "Economics \t", "Education", "Ethnic Studies \t", "Film Studies \t", "Gender Studies \t", "Geography \t", "History", "Labor Studies \t", "Law \t", "Literature \t", "Manuscript Studies \t", "Math", "Music Studies \t", "Philosophy \t", "Political Science \t", "Religious Studies \t", "Science", "Sociology \t", "Sound Studies \t", "Theater Studies"});
		discipline_list.setBounds(97, 231, 217, 103);
		
		
		table = new Table(grpArcStandards, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(446, 160, 217, 174);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		//Let's make the right most column of our roles table editable
	    final int EDITABLECOLUMN = 1;
	    table.addSelectionListener(new SelectionAdapter() {
	        @Override
	        public void widgetSelected(SelectionEvent e) {
	            // Clean up any previous editor control
	            final TableEditor editor = new TableEditor(table);               
	            // The editor must have the same size as the cell and must
	            // not be any smaller than 50 pixels.
	            editor.horizontalAlignment = SWT.LEFT;
	            editor.grabHorizontal = true;
	            editor.minimumWidth = 50;
	            Control oldEditor = editor.getEditor();
	            if (oldEditor != null)
	                oldEditor.dispose();                

	            // Identify the selected row
	            TableItem item = (TableItem) e.item;
	            if (item == null)
	                return;

	            // The control that will be the editor must be a child of the
	            // Table
	            newEditor = new Text(table, SWT.NONE);
	            newEditor.setText(item.getText(EDITABLECOLUMN));

	            newEditor.addModifyListener(new ModifyListener() {
	                public void modifyText(ModifyEvent me) {
	                    Text text = (Text) editor.getEditor();
	                    editor.getItem()
	                            .setText(EDITABLECOLUMN, text.getText());
	                }
	            });         
	            newEditor.selectAll();
	            newEditor.setFocus();           
	            editor.setEditor(newEditor, item, EDITABLECOLUMN);      

	        }
	    });     
		
		tblclmnNewColumn = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn.setWidth(100);
		tblclmnNewColumn.setText("Role");
				
		tblclmnNewColumn_1 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_1.setWidth(100);
		tblclmnNewColumn_1.setText("Name");
	
		group = new Group(shlMusoCatologuer, SWT.NONE);
		fd_grpArcStandards.right = new FormAttachment(group, 0, SWT.RIGHT);
		fd_grpArcStandards.top = new FormAttachment(group, 6);
		FormData fd_group = new FormData();
		fd_group.bottom = new FormAttachment(100, -690);
		fd_group.top = new FormAttachment(0);
		fd_group.right = new FormAttachment(100, -23);
		fd_group.left = new FormAttachment(0, 10);
		
		text_13 = new Text(grpMusoStandards, SWT.BORDER);
		text_13.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				created_value = text_13.getText().trim();
			}
		});
		text_13.setMessage("Value");
		text_13.setBounds(207, 192, 107, 26);
		
		text_12 = new Text(grpMusoStandards, SWT.BORDER);
		text_12.setBounds(97, 231, 217, 26);
		
		label_6 = new Label(grpMusoStandards, SWT.NONE);
		label_6.setText("Other ID:");
		label_6.setBounds(10, 234, 59, 20);
		text_12.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				other_id = text_12.getText().trim();
			}
		});
		group.setLayoutData(fd_group);
		
		lblDataAndDigital = new Label(group, SWT.NONE);
		lblDataAndDigital.setForeground(SWTResourceManager.getColor(0, 51, 102));
		lblDataAndDigital.setBounds(328, 39, 340, 51);
		lblDataAndDigital.setFont(SWTResourceManager.getFont("Segoe UI Semibold", 23, SWT.BOLD));
		lblDataAndDigital.setText("Digital Scholars Lab");
		
		lblNewLabel_3 = new Label(group, SWT.NONE);
		lblNewLabel_3.setImage(SWTResourceManager.getImage(cataloguer_swt.class, "/ucdavis_logo.jpg"));
		lblNewLabel_3.setBounds(10, 20, 312, 94);
		//lblNewLabel_3.setImage
		
		roles = new String[] {"Visual Artist", "Author ", "Editor ", "Publisher ", "Translator ", "Creator ", "Etcher ", "Engraver ", "Owner ", "Artist ", "Architect ", "Binder ", "Book designer ", "Book producer  ", "Calligrapher ", "Cartographer ", "Collector ", "Colorist ", "Commentator for written text ", "Compiler ", "Compositor ", "Creator ", "Dubious author ", "Facsimilist ", "Illuminator ", "Illustrator ", "Lithographer ", "Printer ", "Printer of plates ", "Printmaker ", "Repository ", "Rubricator ", "Scribe", "Sculptor", "Type designer", "Typographer", "Wood engraver", "Wood cutter"};
		
		for (int i = 0; i < roles.length; ++i) {
		   TableItem item = new TableItem(table, SWT.NONE);
		   item.setText(roles[i].trim());
		}
		
		
	}
}
