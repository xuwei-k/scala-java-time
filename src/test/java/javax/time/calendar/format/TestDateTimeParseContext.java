/*
 * Copyright (c) 2008-2011, Stephen Colebourne & Michael Nascimento Santos
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  * Neither the name of JSR-310 nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package javax.time.calendar.format;

import static javax.time.calendar.ISODateTimeRule.DAY_OF_MONTH;
import static javax.time.calendar.ISODateTimeRule.MONTH_OF_YEAR;
import static javax.time.calendar.ISODateTimeRule.YEAR;
import static org.testng.Assert.assertEquals;

import java.util.Locale;
import java.util.Map;

import javax.time.calendar.CalendricalRule;
import javax.time.calendar.ZoneId;
import javax.time.calendar.ZoneOffset;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test DateTimeParseContext.
 *
 * @author Stephen Colebourne
 */
@Test
public class TestDateTimeParseContext {

    private DateTimeFormatSymbols symbols;
    private DateTimeParseContext context;

    @BeforeMethod
    public void setUp() {
        symbols = DateTimeFormatSymbols.of(Locale.GERMANY);
        context = new DateTimeParseContext(symbols);
    }

    //-----------------------------------------------------------------------
    public void test_constructor() throws Exception {
        assertEquals(context.getSymbols(), symbols);
        assertEquals(context.getLocale(), Locale.GERMANY);
        assertEquals(context.getParsedRules().size(), 0);
    }

    @Test(expectedExceptions=NullPointerException.class)
    public void test_constructor_null() throws Exception {
        new DateTimeParseContext(null);
    }

    //-----------------------------------------------------------------------
    public void test_caseSensitive() throws Exception {
        assertEquals(context.isCaseSensitive(), true);
        
        context.setCaseSensitive(false);
        
        assertEquals(context.isCaseSensitive(), false);
    }

    //-----------------------------------------------------------------------
    public void test_strict() throws Exception {
        assertEquals(context.isStrict(), true);
        
        context.setStrict(false);
        
        assertEquals(context.isStrict(), false);
    }

    //-----------------------------------------------------------------------
    public void test_fields_oneField() throws Exception {
        context.setParsedField(YEAR, 2008);
        
        assertEquals(context.getParsedRules().size(), 1);
        assertEquals(context.getParsed(YEAR), YEAR.field(2008L));
        Map<CalendricalRule<?>, Object> map = context.toCalendricalMerger().getInputMap();
        assertEquals(map.size(), 1);
        assertEquals(map.get(YEAR), YEAR.field(2008L));
        //  test cloned and modifiable
        map.clear();
        assertEquals(map.size(), 0);
        assertEquals(context.getParsedRules().size(), 1);
    }

    public void test_fields_twoFields() throws Exception {
        context.setParsedField(YEAR, 2008);
        context.setParsedField(MONTH_OF_YEAR, 6);
        
        assertEquals(context.getParsedRules().size(), 2);
        assertEquals(context.getParsed(YEAR), YEAR.field(2008L));
        assertEquals(context.getParsed(MONTH_OF_YEAR), MONTH_OF_YEAR.field(6L));
        Map<CalendricalRule<?>, Object> map = context.toCalendricalMerger().getInputMap();
        assertEquals(map.size(), 2);
        assertEquals(map.get(YEAR), YEAR.field(2008L));
        assertEquals(map.get(MONTH_OF_YEAR), MONTH_OF_YEAR.field(6L));
        //  test cloned and modifiable
        map.clear();
        assertEquals(map.size(), 0);
        assertEquals(context.getParsedRules().size(), 2);
        assertEquals(context.getParsed(YEAR), YEAR.field(2008L));
        assertEquals(context.getParsed(MONTH_OF_YEAR), MONTH_OF_YEAR.field(6L));
    }

    @Test(expectedExceptions=NullPointerException.class)
    public void test_fields_getNull() throws Exception {
        context.getParsed(null);
    }

    public void test_fields_get_notPresent() throws Exception {
        assertEquals(context.getParsed(DAY_OF_MONTH), null);
    }

    @Test(expectedExceptions=NullPointerException.class)
    public void test_fields_setNull() throws Exception {
        context.setParsedField(null, 2008);
    }

    //-----------------------------------------------------------------------
    public void test_getParsedRules_set() throws Exception {
        context.setParsedField(DAY_OF_MONTH, 2);
        
        assertEquals(context.getParsedRules().size(), 1);
        assertEquals(context.getParsedRules().iterator().next(), DAY_OF_MONTH);
    }

    @Test(expectedExceptions=UnsupportedOperationException.class)
    public void test_getParsedRules_noAdd() throws Exception {
        context.getParsedRules().add(MONTH_OF_YEAR);
    }

    public void test_getParsedRules_remove() throws Exception {
        context.setParsedField(DAY_OF_MONTH, 2);
        context.getParsedRules().remove(DAY_OF_MONTH);
    }

    //-----------------------------------------------------------------------
    public void test_offset() throws Exception {
        assertEquals(context.getParsed(ZoneOffset.rule()), null);
        
        context.setParsed(ZoneOffset.rule(), ZoneOffset.ofHours(18));
        
        assertEquals(context.getParsed(ZoneOffset.rule()), ZoneOffset.ofHours(18));
    }

    //-----------------------------------------------------------------------
    public void test_zone() throws Exception {
        assertEquals(context.getParsed(ZoneId.rule()), null);
        
        context.setParsed(ZoneId.rule(), ZoneId.of(ZoneOffset.ofHours(18)));
        
        assertEquals(context.getParsed(ZoneId.rule()), ZoneId.of(ZoneOffset.ofHours(18)));
    }

    //-----------------------------------------------------------------------
    public void test_toString() throws Exception {
        context.setParsedField(YEAR, 2008);
        context.setParsedField(MONTH_OF_YEAR, 6);
        context.setParsed(ZoneOffset.rule(), ZoneOffset.ofHours(16));
        context.setParsed(ZoneId.rule(),ZoneId.of(ZoneOffset.ofHours(18)));
        
        String str = context.toString();
        assertEquals(str.contains("MonthOfYear 6"), true);
        assertEquals(str.contains("Year 2008"), true);
        assertEquals(str.contains("UTC+18:00"), true);
        assertEquals(str.contains("+16:00"), true);
    }

}
