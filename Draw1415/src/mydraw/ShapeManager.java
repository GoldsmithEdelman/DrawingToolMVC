package mydraw;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Hashtable;

/**
 * This class determines how mouse events are to be interpreted,
 * depending on the shape mode currently set 
 * @param itsGUI User-interface reference
 */

class ShapeManager implements ItemListener
{
    private DrawGUI _shapeManagerGUI;
    private Hashtable<String, ShapeDrawer> _shape;
    private ScribbleDrawer _scribbleDrawer;
    private RectangleDrawer _rectDrawer;
    private OvalDrawer _ovalDrawer;
    private ShapeDrawer _currentDrawer;
    private TriangleDrawer _triangleDrawer;
    private FillRecDrawer _filledRectangleDrawer;
    private LineDrawer _lineDrawer;

    /**
     * Constructor
     * @param itsGUI
     */
    public ShapeManager(DrawGUI itsGUI)
    {
        _shapeManagerGUI = itsGUI;
        _scribbleDrawer = new ScribbleDrawer(_shapeManagerGUI);
        _rectDrawer = new RectangleDrawer(_shapeManagerGUI);
        _ovalDrawer = new OvalDrawer(_shapeManagerGUI);
        _triangleDrawer = new TriangleDrawer(_shapeManagerGUI);
        _filledRectangleDrawer = new FillRecDrawer(_shapeManagerGUI);
        _lineDrawer = new LineDrawer(_shapeManagerGUI);
        // default: scribble mode
        _currentDrawer = _scribbleDrawer;
        // activate scribble drawer
        _shapeManagerGUI.getDrawingArea()
            .addMouseListener(_currentDrawer);
        _shapeManagerGUI.getDrawingArea()
            .addMouseMotionListener(_currentDrawer);
        shapesHashTable();
    }

    private void shapesHashTable()
    {
        _shape = new Hashtable<String, ShapeDrawer>();
        _shape.put("Scribble", _scribbleDrawer);
        _shape.put("Rectangle", _rectDrawer);
        _shape.put("Oval", _ovalDrawer);
        _shape.put("Triangle", _triangleDrawer);
        _shape.put("FilledRectangle", _filledRectangleDrawer);
        _shape.put("Line", _lineDrawer);
    }

    public void setCurrentDrawer(ShapeDrawer l)
    {
        if (_currentDrawer == l) return;

        // deactivate previous drawer
        _shapeManagerGUI.getDrawingArea()
            .removeMouseListener(_currentDrawer);
        _shapeManagerGUI.getDrawingArea()
            .removeMouseMotionListener(_currentDrawer);

        // activate new drawer
        _currentDrawer = l;
        _shapeManagerGUI.getDrawingArea()
            .addMouseListener(_currentDrawer);
        _shapeManagerGUI.getDrawingArea()
            .addMouseMotionListener(_currentDrawer);
    }

    // user selected new shape => reset the shape mode
    public void itemStateChanged(ItemEvent e)
    {
        setCurrentDrawer(_shape.get(e.getItem()));

    }
}
