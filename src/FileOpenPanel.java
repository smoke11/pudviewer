import smoke11.DebugView;
import smoke11.pudparser.PudParser;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

public class FileOpenPanel extends JPanel
{
    public File OpenedMapFile;
    public File OpenedXMLFile;
    private boolean readingMap = false;
    public String getPathToJar()
    {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Show path to data files");
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
      // FileFilter filter1 = new ExtensionFileFilter(".jar", new String[] { "jar" });
       // fileChooser.setFileFilter(filter1);
        fileChooser.setCurrentDirectory(new File(FileOpenPanel.class.getProtectionDomain().getCodeSource().getLocation().getPath()));

        fileChooser.setAccessory(new LabelAccessory(fileChooser));

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            String path = fileChooser.getCurrentDirectory().getPath()+"\\";
            DebugView.writeDebug(DebugView.DEBUGLVL_LESSINFO, this.getClass().getName(), "Using directory: " + path);
            return path;
        }
        return null;
    }
    public void openXMLFile(String dir) {
        JFileChooser fileChooser = new JFileChooser();
        FileFilter filter1 = new ExtensionFileFilter(".xml", new String[] { "xml" });
        fileChooser.setDialogTitle("Open settings.xml");
        fileChooser.setFileFilter(filter1);


        fileChooser.setCurrentDirectory(new File(dir));
        fileChooser.setAccessory(new LabelAccessory(fileChooser));

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            OpenedXMLFile = fileChooser.getSelectedFile();
            DebugView.writeDebug(DebugView.DEBUGLVL_LESSINFO, this.getClass().getSimpleName(), "Selected file: " + OpenedXMLFile.getAbsolutePath());
        }
    }
    public void openXMLFile() {
        JFileChooser fileChooser = new JFileChooser();
        FileFilter filter1 = new ExtensionFileFilter(".xml", new String[] { "xml" });
        fileChooser.setDialogTitle("Open settings.xml");
        fileChooser.setFileFilter(filter1);
        String fulldir = FileOpenPanel.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String dironly = fulldir.substring(0, fulldir.lastIndexOf("/") + 1);
        String resultdir;
        if(fulldir.contains(".jar".toLowerCase()))  //to fix bug with name of jar when running from jar
            resultdir = (dironly);
        else
            resultdir = (fulldir);

        fileChooser.setCurrentDirectory(new File(resultdir));
        fileChooser.setAccessory(new LabelAccessory(fileChooser));

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            OpenedXMLFile = fileChooser.getSelectedFile();
            DebugView.writeDebug(DebugView.DEBUGLVL_LESSINFO, this.getClass().getSimpleName(), "Selected file: " + OpenedXMLFile.getAbsolutePath());
        }
    }
    public void openMapFile(String dir) {
        JFileChooser fileChooser = new JFileChooser();
        FileFilter filter1 = new ExtensionFileFilter(".pud", new String[] { "pud" });
        fileChooser.setDialogTitle("Open map file");
        fileChooser.setFileFilter(filter1);
        readingMap=true;

        fileChooser.setCurrentDirectory(new File(dir));
        fileChooser.setAccessory(new LabelAccessory(fileChooser));

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            OpenedMapFile = fileChooser.getSelectedFile();
            DebugView.writeDebug(DebugView.DEBUGLVL_LESSINFO, this.getClass().getSimpleName(), "Selected file: " + OpenedMapFile.getAbsolutePath());
        }
        readingMap=false;
    }

}
class ExtensionFileFilter extends FileFilter {
    String description;

    String extensions[];

    public ExtensionFileFilter(String description, String extension) {
        this(description, new String[] { extension });
    }

    public ExtensionFileFilter(String description, String extensions[]) {
        if (description == null) {
            this.description = extensions[0];
        } else {
            this.description = description;
        }
        this.extensions = extensions.clone();
        toLower(this.extensions);
    }

    private void toLower(String array[]) {
        for (int i = 0, n = array.length; i < n; i++) {
            array[i] = array[i].toLowerCase();
        }
    }

    public String getDescription() {
        return description;
    }

    public boolean accept(File file) {
        if (file.isDirectory()) {
            return true;
        } else {
            String path = file.getAbsolutePath().toLowerCase();
            for (int i = 0, n = extensions.length; i < n; i++) {
                String extension = extensions[i];
                if ((path.endsWith(extension) && (path.charAt(path.length() - extension.length() - 1)) == '.')) {
                    return true;
                }
            }
        }
        return false;
    }
}
class LabelAccessory extends JLabel implements PropertyChangeListener {
    private static final int PREFERRED_WIDTH = 175;
    private static final int PREFERRED_HEIGHT = 100;

    public LabelAccessory(JFileChooser chooser) {
        setVerticalAlignment(JLabel.CENTER);
        setHorizontalAlignment(JLabel.CENTER);
        chooser.addPropertyChangeListener(this);
        setPreferredSize(new Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT));

    }


    public void propertyChange(PropertyChangeEvent changeEvent) {
            String changeName = changeEvent.getPropertyName();
            String text = "";
            if (changeName.equals(JFileChooser.SELECTED_FILE_CHANGED_PROPERTY)) {
                File file=null;
                file = (File)changeEvent.getNewValue();
                if (file != null&&file.isFile())
                {
                    String ext = file.getName().substring(file.getName().lastIndexOf('.'));
                    if(ext.equalsIgnoreCase(".pud"))    //extension==.pud then read data from file
                    {
                        PudParser p = new PudParser();
                        p.getInfoFromFile(file);
                        text="Title: "+p.pudTitle +"<br />pudDesc: "+p.pudDesc +"<br />Dimension: "+p.dimX +"x"+p.dimY +"<br />Terrain Type: "+p.terrainType +"<br />Use custom data:<br />Units: "+p.customUnitData +"<br />Upgrades: "+p.customUpgradeData +"<br />Num. of Units on map: "+p.numberofUnitsOnMap;           //hack with html to make new line
                    }
                }
            }
                    String labeltext = String.format("<html><div WIDTH=%d>%s</div><html>", PREFERRED_WIDTH, text);      //hack, making text wrap       http://stackoverflow.com/questions/2420742/make-a-jlabel-wrap-its-text-by-setting-a-max-width
                    setText(labeltext);
            }


    }
