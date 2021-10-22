package com.bstek.bdf3.security.ui.utils;

import org.apache.commons.lang.StringUtils;
import com.bstek.dorado.data.type.AggregationDataType;
import com.bstek.dorado.data.type.DataType;
import com.bstek.dorado.data.type.EntityDataType;
import com.bstek.dorado.data.type.property.PropertyDef;
import com.bstek.dorado.view.View;
import com.bstek.dorado.view.widget.data.DataSet;
import com.bstek.dorado.view.widget.datacontrol.DataControl;
import com.bstek.dorado.view.widget.form.autoform.AutoForm;

public class DoradoUtil {
  public static EntityDataType getEntityDataType(View view, DataControl dataControl) {
    String dataSetId = dataControl.getDataSet();
    DataSet dataSet = (DataSet) view.getViewElement(dataSetId);
    DataType dataType = dataSet.getDataType();

    EntityDataType entityDataType = getEntityDataType(dataType);
    String dataPath = dataControl.getDataPath();


    if (StringUtils.isNotEmpty(dataPath) && entityDataType != null) {
      entityDataType = findEntityDataTypeByDataPath(entityDataType, dataPath);
    }
    return entityDataType;
  }

  public static EntityDataType findEntityDataTypeByDataPath(EntityDataType entityDataType, String dataPath) {
    String[] properties = null;
    if (StringUtils.isNotEmpty(dataPath)) {
      dataPath = dataPath.replaceAll("#", "");
      dataPath = dataPath.replaceAll("!\\w+", "");
      properties = dataPath.split("\\.");
    }

    if (properties != null) {
      for (String protpery : properties) {
        PropertyDef propertyDef = entityDataType.getPropertyDef(protpery);
        if (propertyDef != null) {
          entityDataType = getEntityDataType(propertyDef.getDataType());
          if (entityDataType == null) {
            break;
          }
        } else {
          break;
        }
      }
    }

    return entityDataType;
  }

  public static String getLabel(EntityDataType entityDataType, String property) {
    if (StringUtils.isEmpty(property)) {
      return null;
    }
    if (!property.contains(".")) {
      PropertyDef propertyDef = entityDataType.getPropertyDef(property);
      if (propertyDef != null) {
        return propertyDef.getLabel();
      }
    } else {
      int lastIndexOfDot = property.lastIndexOf('.');
      String dataPath = property.substring(0, lastIndexOfDot);
      String prop = property.substring(lastIndexOfDot + 1);

      EntityDataType entityDataType2 = findEntityDataTypeByDataPath(entityDataType, dataPath);
      if (entityDataType2 != null) {
        return getLabel(entityDataType2, prop);
      }
    }
    return null;

  }

  public static EntityDataType getEntityDataType(DataType dataType) {
    EntityDataType entityDataType = null;
    if (dataType instanceof AggregationDataType) {
      dataType = ((AggregationDataType) dataType).getElementDataType();

    }
    if (dataType instanceof EntityDataType) {
      entityDataType = (EntityDataType) dataType;
    }
    return entityDataType;
  }

  public static class AutoFormDataControlWrapper implements DataControl {

    public AutoFormDataControlWrapper(AutoForm autoForm) {
      this.autoForm = autoForm;
    }

    private AutoForm autoForm;

    @Override
    public String getDataSet() {
      return autoForm.getDataSet();
    }

    @Override
    public void setDataSet(String dataSet) {
      autoForm.setDataSet(dataSet);
    }

    @Override
    public String getDataPath() {
      return autoForm.getDataPath();
    }

    @Override
    public void setDataPath(String dataPath) {
      autoForm.setDataPath(dataPath);
    }

  }

}
