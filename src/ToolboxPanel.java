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
public class ToolboxPanel extends JPanel implements ActionListener {
    private ArrayList _mapViewListeners = new ArrayList();
    private ArrayList _mainWindowListeners = new ArrayList();
    public ToolboxPanel(Dimension d)
    {
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        this.setPreferredSize(d);
        this.setLayout(new GridLayout(0,8));

        this.setVisible(true);
        JButton showID = new JButton();
        showID.setText("Show ID");
        showID.addActionListener(this);

        JButton loadMap = new JButton();
        loadMap.setText("Load Map");
        loadMap.addActionListener(this);

        super.add(showID);
        super.add(loadMap);


    }
    public synchronized void addEventListener(ToolboxListenerMapPanel listener)  {
        	    _mapViewListeners.add(listener);
        	  }
    public synchronized void removeEventListener(ToolboxListenerMapPanel listener)   {
        _mapViewListeners.remove(listener);
    	  }
    public synchronized void addEventListener(ToolboxListenerMainWindow listener)  {
        _mainWindowListeners.add(listener);
    }
    public synchronized void removeEventListener(ToolboxListenerMainWindow listener)   {
        _mainWindowListeners.remove(listener);
    }
    private synchronized void fireEvent(String whatToFire)	{
        ToolboxEvents e = new ToolboxEvents(this);
        Iterator i1 = _mapViewListeners.iterator();
        Iterator i2 = _mainWindowListeners.iterator();
            if(whatToFire.equalsIgnoreCase("Show ID"))
            {
                while(i1.hasNext())	{
                ((ToolboxListenerMapPanel) i1.next()).buttonPressed(e);
                }
            }
            else
            {
                while(i2.hasNext())	{
                ((ToolboxListenerMainWindow) i2.next()).loadMap(e);
                }
            }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton b = (JButton)e.getSource();
        fireEvent(b.getText());
    }
}
