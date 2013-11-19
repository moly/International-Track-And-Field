/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.thomasweston;

import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;

/**
 *
 * @author Ishmael
 */
public class SaveFile 
{
    private final RecordStore _recordStore;
    private final static int NUM_RECORDS = 5;
    
    public static SaveFile open(String name) throws RecordStoreException
    {
        return new SaveFile(RecordStore.openRecordStore(name, true));
    }
    
    private SaveFile(RecordStore recordStore)
    {
        _recordStore = recordStore;
        
        try
        {    
            if(_recordStore.getNumRecords() == 0)
                init();
        }
        catch(RecordStoreException ex)
        {
            throw new RuntimeException(ex.getMessage());
        }
    }
    
    private void init() throws RecordStoreException
    {
        // if the record is empty fill it with some data
        byte[] b = makeByte4FromInt(60000);
        for(int i = 0; i < NUM_RECORDS; i++)
            _recordStore.addRecord(b, 0, b.length);
    }
    
    public void close()
    {
        try 
        {
            _recordStore.closeRecordStore();
        } 
        catch(RecordStoreException ex)
        {
            System.out.println(ex.getMessage());
        }
    }
    
    public void setTimes(int[] times)
    {
        try 
        {
            RecordEnumeration re = _recordStore.enumerateRecords(null, null, false);
            
            int q = 0;
            while(re.hasNextElement())
            {
                byte[] l = makeByte4FromInt(times[q++]);
                _recordStore.setRecord(re.nextRecordId(), l, 0, l.length);
            }
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }
    
    public int[] getTimes()
    {
        int[] times = new int[NUM_RECORDS];
        try 
        {
            // access record and put it in array
            RecordEnumeration re = _recordStore.enumerateRecords(null, null, false);
            int i = 0;
            while(re.hasNextElement())
                times[i++] = makeIntFromByte4(re.nextRecord());
        }
        catch(RecordStoreException ex)
        {
            System.out.println(ex.getMessage());
        }
        return times;
    }
    
    // some int/byte functions found on the internet
    private int makeIntFromByte4(byte[] b) 
    {
        return b[0]<<24 | (b[1]&0xff)<<16 | (b[2]&0xff)<<8 | (b[3]&0xff);
    }

    private byte[] makeByte4FromInt(int i)
    {
        return new byte[] { (byte)(i>>24), (byte)(i>>16), (byte)(i>>8), (byte)i };
    }
}
