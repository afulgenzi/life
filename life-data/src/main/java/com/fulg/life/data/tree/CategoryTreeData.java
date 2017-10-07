package com.fulg.life.data.tree;

import com.fulg.life.data.ItemData;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by alessandro.fulgenzi on 03/07/16.
 */
public class CategoryTreeData extends ItemData implements Comparable<CategoryTreeData> {
    private String code;
    private String text;
    private boolean selectable = true;
    private List<CategoryTreeData> nodes = null;
    private CategoryState state;

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public boolean isSelectable()
    {
        return selectable;
    }

    public void setSelectable(boolean selectable)
    {
        this.selectable = selectable;
    }

    public List<CategoryTreeData> getNodes()
    {
        return nodes;
    }

    public void setNodes(List<CategoryTreeData> nodes)
    {
        this.nodes = nodes;
    }

    public CategoryState getState()
    {
        return state;
    }

    public void setState(CategoryState state)
    {
        this.state = state;
    }

    public int compareTo(CategoryTreeData item)
    {
        if (this.text.compareTo(item.getText()) == 0)
        {
            return this.code.compareTo(item.getCode());
        } else
        {
            return this.text.compareTo(item.getText());
        }
    }

    public static class CategoryState {
        private boolean selected = false;

        public boolean isSelected()
        {
            return selected;
        }

        public void setSelected(boolean selected)
        {
            this.selected = selected;
        }
    }
}
