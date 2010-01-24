package se.lbroman.msrp.impl.data.header;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ByteRangeHeaderImplTest {
    
    @Test
    public void encodeUndecidedRange() {
        ByteRangeHeaderImpl header = new ByteRangeHeaderImpl();
        header.setStart(1);
        header.setEnd(-1);
        header.setTotal(-1);
        assertEquals(header.getValue(),"1-*/*");
    }

}
