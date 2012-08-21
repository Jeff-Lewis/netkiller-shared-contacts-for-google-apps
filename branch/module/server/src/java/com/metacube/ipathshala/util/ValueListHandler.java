package com.metacube.ipathshala.util;



import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import com.metacube.ipathshala.PaginationInfo;
import com.metacube.ipathshala.core.AppException;
import com.metacube.ipathshala.search.FilterRecordRange;

public class ValueListHandler
implements ValueListIterator {

  protected List<Object> list;
  protected ListIterator listIterator;
  protected FilterRecordRange recordRange;

  public ValueListHandler() {
  }

  protected void setList(List<Object> list) 
  throws AppException {
    this.list = list;
    if(list != null)
      listIterator =  list.listIterator();
    else
      throw new AppException("List empty");
  }

  public Collection<Object> getList(){
    return list;
  }
    
  public int getSize() throws AppException{
    int size = 0;
        
    if (list != null)
      size = list.size();
    else
      throw new AppException(""); //No Data

    return size;
  }
    
  public Object getCurrentElement() 
  throws AppException {

    Object obj = null;
    // Will not advance iterator
    if (list != null)
    {
      int currIndex = listIterator.nextIndex();
      obj = list.get(currIndex);
    }
    else
      throw new AppException("");
    return obj;

  }
    
  public List getPreviousElements(int count) 
  throws AppException {
    int i = 0;
    Object object = null;
    LinkedList list = new LinkedList();
    if (listIterator != null) {
      while (listIterator.hasPrevious() && (i < count)){
        object = listIterator.previous();
        list.add(object);
        i++;
      }
    }// end if
    else
      throw new AppException(""); // No data

    return list;
  }
    
  public List getNextElements(int count) 
  throws AppException {
    int i = 0;
    Object object = null;
    LinkedList list = new LinkedList();
    if(listIterator != null){
      while(  listIterator.hasNext() && (i < count) ){
        object = listIterator.next();
        list.add(object);
        i++;
      }
    } // end if
    else
      throw new AppException(""); // No data

    return list;
  }
    
  public void resetIndex() throws AppException{
    if(listIterator != null){
      listIterator = list.listIterator();
    }
    else
      throw new AppException(""); // No data
  }

@Override
public List<Object> getCurrentPageRecords(PaginationInfo pageInfo) {
	List results = null;
	if (pageInfo != null) {
		int startRecord = (pageInfo.getPageNumber() - 1) * pageInfo.getRecordsPerPage();
		int endRecord = startRecord + pageInfo.getRecordsPerPage();
		if(list.size()<=endRecord)
			endRecord = list.size();
		recordRange = new FilterRecordRange(startRecord, endRecord);
		results = list.subList(startRecord, endRecord);
	}
	
	return results;
}

public FilterRecordRange getRecordRange() {
	return recordRange;
}

}