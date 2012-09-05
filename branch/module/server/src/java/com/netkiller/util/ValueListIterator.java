package com.netkiller.util;



import java.util.List;

import com.netkiller.PaginationInfo;
import com.netkiller.core.AppException;

public interface ValueListIterator {
    
  public int getSize() 
    throws AppException;
    
  public Object getCurrentElement() 
    throws AppException;
    
  public List getPreviousElements(int count) 
    throws AppException;
    
  public List getNextElements(int count) 
    throws AppException;
    
  public void resetIndex()
    throws AppException;
  
  public List getCurrentPageRecords(PaginationInfo paginationInfo);

}
