/*
 * Copyright 2003 - 2014 The eFaps Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Revision:        $Rev$
 * Last Changed:    $Date$
 * Last Changed By: $Author$
 */


package org.efaps.ui.wicket.components.bpm.process;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.PageReference;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackDefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.efaps.admin.dbproperty.DBProperties;
import org.efaps.ui.wicket.components.AbstractSortableProvider;
import org.efaps.ui.wicket.models.objects.UIVariableInstanceLog;
import org.efaps.ui.wicket.resources.AbstractEFapsHeaderItem;
import org.efaps.ui.wicket.resources.EFapsContentReference;
import org.efaps.util.EFapsException;


/**
 * TODO comment!
 *
 * @author The eFaps Team
 * @version $Id$
 */
public class VariableTablePanel
    extends Panel
{

    /**
     * Reference to the style sheet.
     */
    public static final EFapsContentReference CSS = new EFapsContentReference(AbstractSortableProvider.class,
                    "BPM.css");


    /**
     * Needed for serialization.
     */
    private static final long serialVersionUID = 1L;

    /**
     * DataProvier for this TaskTable.
     */
    private final VariableInstanceProvider dataProvider;

    /**
     * @param _wicketId wicket for this component
     * @param _pageReference Reference to the calling page
     * @param _dataProvider provider for the task table
     * @throws EFapsException on error
     */
    public VariableTablePanel(final String _wicketId,
                              final PageReference _pageReference,
                              final VariableInstanceProvider _dataProvider)
        throws EFapsException
    {
        super(_wicketId);
        this.dataProvider = _dataProvider;

        final List<IColumn<UIVariableInstanceLog, String>> columns = new ArrayList<>();
        columns.add(new PropertyColumn<UIVariableInstanceLog, String>(new Model<>("ID"), "id", "id"));

        final String variableInstanceId = DBProperties.getProperty(VariableTablePanel.class.getName()
                        + ".Node.VariableInstanceId");
        final String variableId = DBProperties.getProperty(VariableTablePanel.class.getName() + ".Node.VariableId");
        final String value = DBProperties.getProperty(VariableTablePanel.class.getName() + ".Node.Value");
        final String date = DBProperties.getProperty(VariableTablePanel.class.getName() + ".Node.Date");

        columns.add(new PropertyColumn<UIVariableInstanceLog, String>(new Model<>(variableInstanceId),
                        "variableInstanceId",
                        "variableInstanceId"));
        columns.add(new PropertyColumn<UIVariableInstanceLog, String>(new Model<>(variableId), "variableId",
                        "variableId"));
        columns.add(new PropertyColumn<UIVariableInstanceLog, String>(new Model<>(value), "value",
                        "value"));
        columns.add(new PropertyColumn<UIVariableInstanceLog, String>(new Model<>(date), "date",
                        "date"));

        add(new AjaxFallbackDefaultDataTable<>("table", columns, this.dataProvider,
           this.dataProvider.getRowsPerPage()));
    }


    /**
     * @return update the underlying data
     */
    public boolean updateData()
    {
        this.dataProvider.requery();
        return true;
    }

    @Override
    public void renderHead(final IHeaderResponse _response)
    {
        super.renderHead(_response);
        _response.render(AbstractEFapsHeaderItem.forCss(VariableTablePanel.CSS));
    }
}
