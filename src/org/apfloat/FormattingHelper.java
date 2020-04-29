/*
 * Apfloat arbitrary precision arithmetic library
 * Copyright (C) 2002-2017  Mikko Tommila
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package org.apfloat;

import java.io.Closeable;
import java.io.Flushable;
import java.io.Writer;
import java.io.FilterWriter;
import java.io.StringWriter;
import java.io.IOException;
import java.text.DecimalFormatSymbols;
import java.util.Formatter;
import java.util.Locale;

/**
 * Helper class for formatting.
 *
 * @version 1.8.3
 * @author Mikko Tommila
 */

class FormattingHelper
{
    private static class AppendableWriter
        extends Writer
    {
        public AppendableWriter(Appendable out)
        {
            this.out = out;
        }

        @Override
        public void write(int c)
            throws IOException
        {
            this.out.append((char) c);
        }

        @Override
        public void write(char[] buffer, int offset, int length)
            throws IOException
        {
            for (int i = 0; i < length; i++)
            {
                this.out.append(buffer[i + offset]);
            }
        }

        @Override
        public void write(String text, int offset, int length)
            throws IOException
        {
            this.out.append(text, offset, length);
        }

        @Override
        public Writer append(CharSequence sequence)
            throws IOException
        {
            this.out.append(sequence);
            return this;
        }

        @Override
        public Writer append(CharSequence sequence, int start, int end)
            throws IOException
        {
            this.out.append(sequence, start, end);
            return this;
        }

        @Override
        public void flush()
            throws IOException
        {
            if (this.out instanceof Flushable)
            {
                ((Flushable) this.out).flush();
            }
        }

        @Override
        public void close()
            throws IOException
        {
            if (this.out instanceof Closeable)
            {
                ((Closeable) this.out).close();
            }
        }

        private Appendable out;
    }

    private static class LocalizeWriter
        extends FilterWriter
    {
        public LocalizeWriter(Writer out, Locale locale, boolean localizeDigits, boolean isUpperCase)
        {
            super(out);
            this.locale = locale;
            this.localizeDigits = localizeDigits;
            this.isUpperCase = isUpperCase;
            if (locale != null)
            {
                DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(locale);
                this.zero = decimalFormatSymbols.getZeroDigit();
                this.decimalSeparator = decimalFormatSymbols.getDecimalSeparator();
            }
            else
            {
                this.zero = '0';
                this.decimalSeparator = '.';
            }
        }

        @Override
        public void write(int c)
            throws IOException
        {
            if (c == '.')
            {
                c = this.decimalSeparator;
            }
            else if (this.localizeDigits && c >= '0' && c <= '9')
            {
                c += this.zero - '0';
            }
            if (this.isUpperCase)
            {
                String s;
                if (this.locale == null)
                {
                    s = String.valueOf((char) c).toUpperCase();
                }
                else
                {
                    s = String.valueOf((char) c).toUpperCase(this.locale);
                }
                for (int i = 0; i < s.length(); i++)
                {
                    super.write(s.charAt(i));
                }
            }
            else
            {
                super.write(c);
            }
        }

        @Override
        public void write(char[] buffer, int offset, int length)
            throws IOException
        {
            for (int i = 0; i < length; i++)
            {
                write(buffer[i + offset]);
            }
        }

        @Override
        public void write(String text, int offset, int length)
            throws IOException
        {
            for (int i = 0; i < length; i++)
            {
                write(text.charAt(i + offset));
            }
        }

        private Locale locale;
        private boolean localizeDigits;
        private boolean isUpperCase;
        private char zero;
        private char decimalSeparator;
    }

    private static class CountWriter
        extends FilterWriter
    {
        public CountWriter(Writer out)
        {
            super(out);
        }

        @Override
        public void write(int c)
            throws IOException
        {
            super.write(c);
            this.count++;
        }

        @Override
        public void write(char[] buffer, int offset, int length)
            throws IOException
        {
            super.write(buffer, offset, length);
            this.count += length;
        }

        @Override
        public void write(String text, int offset, int length)
            throws IOException
        {
            super.write(text, offset, length);
            this.count += length;
        }

        public long count()
        {
            return this.count;
        }

        private long count;
    }

    private static class BufferWriter
        extends StringWriter
    {
        public BufferWriter(Writer out)
        {
            this.out = out;
        }

        public Writer out()
        {
            return this.out;
        }

        private Writer out;
    }

    private FormattingHelper()
    {
    }

    public static Writer wrapAppendableWriter(Appendable out)
    {
        return (out instanceof Writer ? (Writer) out : new AppendableWriter(out));
    }

    public static Writer wrapLocalizeWriter(Writer out, Formatter formatter, int radix, boolean isUpperCase)
    {
        return new LocalizeWriter(out, formatter.locale(), radix <= 10, isUpperCase);
    }

    @SuppressWarnings("resource")
    public static Writer wrapPadWriter(Writer out, boolean isLeftJustify)
    {
        if (isLeftJustify)
        {
            out =  new CountWriter(out);
        }
        else
        {
            out = new BufferWriter(out);
        }
        return out;
    }

    public static void finishPad(Writer out, long width)
        throws IOException
    {
        if (out instanceof CountWriter)
        {
            CountWriter counter = (CountWriter) out;
            long count = width - counter.count();
            pad(out, count);
        }
        else
        {
            BufferWriter buffer = (BufferWriter) out;
            long count = width - buffer.getBuffer().length();
            pad(buffer.out(), count);
            buffer.out().append(buffer.getBuffer());
        }
    }

    private static void pad(Appendable out, long count)
        throws IOException
    {
        for (long i = 0; i < count; i++)
        {
            out.append(' ');
        }
    }
}
