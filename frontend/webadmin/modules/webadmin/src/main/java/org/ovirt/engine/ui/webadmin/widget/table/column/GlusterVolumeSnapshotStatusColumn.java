package org.ovirt.engine.ui.webadmin.widget.table.column;

import java.util.Comparator;

import org.ovirt.engine.core.common.businessentities.gluster.GlusterVolumeSnapshotEntity;
import org.ovirt.engine.ui.common.widget.table.column.ColumnWithElementId;
import org.ovirt.engine.ui.common.widget.table.column.SortableColumn;

public class GlusterVolumeSnapshotStatusColumn extends SortableColumn<GlusterVolumeSnapshotEntity, GlusterVolumeSnapshotEntity> implements ColumnWithElementId {

    public GlusterVolumeSnapshotStatusColumn() {
        super(new GlusterVolumeSnapshotStatusCell());
    }

    @Override
    public GlusterVolumeSnapshotEntity getValue(GlusterVolumeSnapshotEntity object) {
        return object;
    }

    public void makeSortable() {
        makeSortable(new Comparator<GlusterVolumeSnapshotEntity>() {
            @Override
            public int compare(GlusterVolumeSnapshotEntity o1, GlusterVolumeSnapshotEntity o2) {
                return o1.getStatus().ordinal() - o2.getStatus().ordinal();
            }
        });
    }

    @Override
    public void configureElementId(String elementIdPrefix, String columnId) {
        getCell().setElementIdPrefix(elementIdPrefix);
        getCell().setColumnId(columnId);
    }

    @Override
    public GlusterVolumeSnapshotStatusCell getCell() {
        return (GlusterVolumeSnapshotStatusCell) super.getCell();
    }
}
