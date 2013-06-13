import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: nao
 * Date: 09.03.13
 * Time: 21:35
 * To change this template use File | Settings | File Templates.
 */
public class ToolboxPanel extends JPanel implements ActionListener, IMapPanelListenerToolbox {
    private ArrayList _mapViewListeners = new ArrayList();
    private ArrayList _mainWindowListeners = new ArrayList();

    public ToolboxPanel(Dimension d)
    {
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        this.setPreferredSize(d);
        this.setLayout(new FlowLayout());
        this.setVisible(true);

        JButton showID = new JButton();
        showID.setText("Show ID");
        showID.addActionListener(this);

        JButton loadMap = new JButton();
        loadMap.setText("Load Map");
        loadMap.addActionListener(this);

        JButton drawTileBox = new JButton();
        drawTileBox.setText("Draw grid");
        drawTileBox.addActionListener(this);

        JButton drawUnitsBox = new JButton();
        drawUnitsBox.setText("Draw Units");
        drawUnitsBox.addActionListener(this);

        JButton biggerFont = new JButton();
        biggerFont.setText("Bigger font");
        biggerFont.addActionListener(this);

        super.add(showID);
        super.add(loadMap);
        super.add(drawTileBox);
        super.add(drawUnitsBox);
        super.add(biggerFont);

    }
    public synchronized void addEventListener(IToolboxListenerMapPanel listener)  {
        	    _mapViewListeners.add(listener);
        	  }
    public synchronized void removeEventListener(IToolboxListenerMapPanel listener)   {
        _mapViewListeners.remove(listener);
    	  }
    public synchronized void addEventListener(IToolboxListenerMainWindow listener)  {
        _mainWindowListeners.add(listener);
    }
    public synchronized void removeEventListener(IToolboxListenerMainWindow listener)   {
        _mainWindowListeners.remove(listener);
    }
    private synchronized void fireEvent(String whatToFire)	{
        ToolboxEvents e = new ToolboxEvents(this);
        Iterator i1 = _mapViewListeners.iterator();
        Iterator i2 = _mainWindowListeners.iterator();
            if(whatToFire.equalsIgnoreCase("Load Map"))
            {
                while(i2.hasNext())	{
                    ((IToolboxListenerMainWindow) i2.next()).loadMap(e);
                }

            }
            else
            {
                if(whatToFire.equalsIgnoreCase("Show ID"))
                    while(i1.hasNext())	{
                        ((IToolboxListenerMapPanel) i1.next()).showID(e);
                    }
                else if(whatToFire.equalsIgnoreCase("Draw grid"))
                    while(i1.hasNext())	{
                        ((IToolboxListenerMapPanel) i1.next()).drawGrid(e);
                    }
                else if(whatToFire.equalsIgnoreCase("Draw Units"))
                    while(i1.hasNext())	{
                        ((IToolboxListenerMapPanel) i1.next()).drawUnits(e);
                    }
                else if(whatToFire.equalsIgnoreCase("Bigger font"))
                    while(i1.hasNext())	{
                        ((IToolboxListenerMapPanel) i1.next()).biggerFont(e);
                    }
            }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton b = (JButton)e.getSource();
        fireEvent(b.getText());
    }

    @Override
    public void showUnitInfo(MapViewEvents e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
