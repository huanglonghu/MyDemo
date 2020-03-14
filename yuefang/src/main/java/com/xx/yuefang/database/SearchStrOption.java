package com.xx.yuefang.database;

import com.xx.yuefang.database.entity.SearchStr;
import com.xx.yuefang.ui.base.YueFangApplication;

import java.util.List;

public class SearchStrOption {

    private SearchStrOption() {
    }

    private static SearchStrOption defaultInstance;

    public static SearchStrOption getInstance() {

        SearchStrOption userOption = defaultInstance;
        if (defaultInstance == null) {
            synchronized (SearchStrOption.class) {
                if (defaultInstance == null) {
                    userOption = new SearchStrOption();
                    defaultInstance = userOption;
                }
            }
        }
        return userOption;
    }

    public void addSearchStr(SearchStr searchStr) {
        SearchStrDao searchStrDao = YueFangApplication.getApplication().getDaoSession().getSearchStrDao();
        searchStrDao.insert(searchStr);
    }

    public SearchStr getSearchStr() {
        SearchStrDao searchStrDao = YueFangApplication.getApplication().getDaoSession().getSearchStrDao();
        List<SearchStr> list = searchStrDao.queryBuilder()
                .list();
        if (list != null&&list.size()>0) {
            return list.get(0);
        } else {
            return null;
        }

    }


}
