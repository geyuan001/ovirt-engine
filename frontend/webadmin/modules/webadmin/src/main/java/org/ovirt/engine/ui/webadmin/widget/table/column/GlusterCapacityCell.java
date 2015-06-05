package org.ovirt.engine.ui.webadmin.widget.table.column;

import org.ovirt.engine.core.common.utils.Pair;
import org.ovirt.engine.core.common.utils.SizeConverter;
import org.ovirt.engine.core.common.utils.SizeConverter.SizeUnit;
import org.ovirt.engine.ui.common.utils.ElementIdUtils;
import org.ovirt.engine.ui.common.widget.table.column.CellWithElementId;
import org.ovirt.engine.ui.webadmin.ApplicationConstants;
import org.ovirt.engine.ui.webadmin.ApplicationMessages;
import org.ovirt.engine.ui.webadmin.ApplicationTemplates;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

public abstract class GlusterCapacityCell<P> extends AbstractCell<P> implements CellWithElementId<P> {

    protected static final ApplicationConstants constants = GWT.create(ApplicationConstants.class);
    protected static final ApplicationTemplates templates = GWT.create(ApplicationTemplates.class);
    protected static final ApplicationMessages messages = GWT.create(ApplicationMessages.class);

    private String elementIdPrefix;
    private String columnId;

    private Double freeSize;
    private Double totalSize;
    private Double usedSize;
    private SizeUnit inUnit;

    protected String getSizeString(Double size, SizeUnit inUnit) {
        if(size == null) {
            return constants.notAvailableLabel();
        } else {
            Pair<SizeUnit, Double> sizeWithUnits = SizeConverter.autoConvert(size.longValue(), inUnit);
            return formatSize(sizeWithUnits.getSecond()) + " " + sizeWithUnits.getFirst().toString();//$NON-NLS-1$
        }
    }

    private String formatSize(double size) {
        return NumberFormat.getFormat("#.##").format(size);//$NON-NLS-1$
    }

    protected String getProgressText(Double freeSize, Double totalSize) {
        if(freeSize == null || totalSize == null) {
            return "?";//$NON-NLS-1$
        } else {
            return ((int)(getPercentageUsage(freeSize, totalSize))) + "%";//$NON-NLS-1$
        }
    }

    protected int getProgressValue(Double freeSize, Double totalSize) {
        if(freeSize == null || totalSize == null) {
            return 0;
        }
        return (int)(Math.round(getPercentageUsage(freeSize, totalSize)));
    }

    private double getPercentageUsage(Double freeSize, Double totalSize) {
        return (((totalSize - freeSize)  * 100 )/totalSize);
    }

    protected void setFreeSize(Double freeSize) {
        this.freeSize = freeSize;
    }

    protected void setTotalSize(Double totalSize) {
        this.totalSize = totalSize;
    }

    protected void setInUnit(SizeUnit inUnit) {
        this.inUnit = inUnit;
    }

    protected void setUsedSize(Double usedSize) {
        this.usedSize = usedSize;
    }

    public void clearAll() {
        setFreeSize(null);
        setTotalSize(null);
        setUsedSize(null);
        setInUnit(null);
    }

    @Override
    public void render(Context context, P value, SafeHtmlBuilder sb) {
        if(value == null) {
            clearAll();
        }
        int progress = getProgressValue(freeSize, totalSize);
        String sizeString = getProgressText(freeSize, totalSize);
        String color = progress < 70 ? "#669966" : progress < 95 ? "#FF9900" : "#FF0000"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        String toolTip = messages.glusterCapacityInfo(getSizeString(freeSize, inUnit), getSizeString(usedSize, inUnit), getSizeString(totalSize, inUnit));
        String id = ElementIdUtils.createTableCellElementId(getElementIdPrefix(), getColumnId(), context);
        SafeHtml safeHtml = templates.glusterCapcityProgressBar(progress, sizeString, color, toolTip, id);
        sb.append(safeHtml);
    }

    @Override
    public void setElementIdPrefix(String elementIdPrefix) {
        this.elementIdPrefix = elementIdPrefix;
    }

    @Override
    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }

    @Override
    public String getElementIdPrefix() {
        return elementIdPrefix;
    }

    @Override
    public String getColumnId() {
        return columnId;
    }
}
