package com.gjc.domain;

import java.util.List;
import java.util.Map;

public class Page {
    private int pageSize = 4;//页面显示多少条记录
    private int totalSize;//共有多少条记录通过读取数据库获得；
    private int currentPage;//当前页码
    //private int totalPage;//共有多少页
    private List<Map<String,Object>> list;//当前页面显示的记录集
    //private int startIndex;//要读取记录的起始值
    private int num = 6;//页码列表的个数
    //private int start;//页码列表的起始值
    //private int end;//页码列表的终止值

    //构造方法，在构造一个Page对象时，应当确定当前页和总记录数
    public Page(int currentPage,int totalSize){
        this.totalSize = totalSize;
        //设置当前页
        setCurrentPage(currentPage);
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        /*
        1.判断当前页码是否合法
        2.当前页码如果小于1，让它默认值为1
        3.当前页码如果大于总页数，让它等于总页数
         */
        int totalPage = getTotalPage();
        if (currentPage < 1){
            currentPage = 1;
        }
        if (currentPage > totalPage){
            currentPage = totalPage;
        }
        this.currentPage = currentPage;
    }

    //获取总页数
    public int getTotalPage() {
        int totalPage = (totalSize%pageSize == 0)?(totalSize/pageSize):(totalSize/pageSize + 1);
        return totalPage;
    }

    public List<Map<String, Object>> getList() {
        return list;
    }

    public void setList(List<Map<String, Object>> list) {
        this.list = list;
    }

    //获取要读取的记录的起始值
    public int getStartIndex() {
        int startIndex = (currentPage - 1)*pageSize;
        if (startIndex < 0){
            startIndex = 0;
        }
        return startIndex;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    //获取页码列表的起始值
    public int getStart() {
        int start = currentPage - num/2 +1;
        if (start < 1){
            start = 1;
        }
        return start;
    }

    //获取页码列表的终止值
    public int getEnd() {
        int end = getStart() + num -1;
        int totalPage = getTotalPage();
        if (end > totalPage){
            end = totalPage;
        }
        return end;
    }

}
