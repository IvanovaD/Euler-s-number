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
package org.apfloat.internal;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

import org.apfloat.ApfloatRuntimeException;
import org.apfloat.spi.DataStorage;
import org.apfloat.spi.ArrayAccess;

/**
 * Disk-based data storage for the <code>double</code> element type.
 *
 * @version 1.8.0
 * @author Mikko Tommila
 */

public class DoubleDiskDataStorage
    extends DiskDataStorage
{
    /**
     * Default constructor.
     */

    public DoubleDiskDataStorage()
        throws ApfloatRuntimeException
    {
    }

    /**
     * Subsequence constructor.
     *
     * @param doubleDiskDataStorage The originating data storage.
     * @param offset The subsequence starting position.
     * @param length The subsequence length.
     */

    protected DoubleDiskDataStorage(DoubleDiskDataStorage doubleDiskDataStorage, long offset, long length)
    {
        super(doubleDiskDataStorage, offset, length);
    }

    @Override
    protected DataStorage implSubsequence(long offset, long length)
        throws ApfloatRuntimeException
    {
        return new DoubleDiskDataStorage(this, offset + getOffset(), length);
    }

    private class DoubleDiskArrayAccess
        extends DoubleMemoryArrayAccess
    {
        // fileOffset is absolute position in file
        public DoubleDiskArrayAccess(int mode, long fileOffset, int length)
            throws ApfloatRuntimeException
        {
            super(new double[length], 0, length);
            this.mode = mode;
            this.fileOffset = fileOffset;

            if ((mode & READ) != 0)
            {
                final double[] array = getDoubleData();
                WritableByteChannel out = new WritableByteChannel()
                {
                    public int write(ByteBuffer buffer)
                    {
                        DoubleBuffer src = buffer.asDoubleBuffer();
                        int readLength = src.remaining();

                        src.get(array, this.readPosition, readLength);

                        this.readPosition += readLength;
                        buffer.position(buffer.position() + readLength * 8);

                        return readLength * 8;
                    }

                    public void close() {}
                    public boolean isOpen() { return true; }

                    private int readPosition = 0;
                };

                transferTo(out, fileOffset * 8, (long) length * 8);
            }
        }

        @Override
        public void close()
            throws ApfloatRuntimeException
        {
            if ((this.mode & WRITE) != 0 && getData() != null)
            {
                final double[] array = getDoubleData();
                ReadableByteChannel in = new ReadableByteChannel()
                {
                    public int read(ByteBuffer buffer)
                    {
                        DoubleBuffer dst = buffer.asDoubleBuffer();
                        int writeLength = dst.remaining();

                        dst.put(array, this.writePosition, writeLength);

                        this.writePosition += writeLength;
                        buffer.position(buffer.position() + writeLength * 8);

                        return writeLength * 8;
                    }

                    public void close() {}
                    public boolean isOpen() { return true; }

                    private int writePosition = 0;
                };

                transferFrom(in, this.fileOffset * 8, (long) array.length * 8);
            }

            super.close();
        }

        private static final long serialVersionUID = -7097317279839657081L;

        private int mode;
        private long fileOffset;
    }

    @Override
    protected ArrayAccess implGetArray(int mode, long offset, int length)
        throws ApfloatRuntimeException
    {
        return new DoubleDiskArrayAccess(mode, getOffset() + offset, length);
    }

    @Override
    protected ArrayAccess createArrayAccess(int mode, int startColumn, int columns, int rows)
    {
        return new MemoryArrayAccess(mode, new double[columns * rows], startColumn, columns, rows);
    }

    @Override
    protected ArrayAccess createTransposedArrayAccess(int mode, int startColumn, int columns, int rows)
    {
        return new TransposedMemoryArrayAccess(mode, new double[columns * rows], startColumn, columns, rows);
    }

    private class MemoryArrayAccess
        extends DoubleMemoryArrayAccess
    {
        public MemoryArrayAccess(int mode, double[] data, int startColumn, int columns, int rows)
        {
            super(data, 0, data.length);
            this.mode = mode;
            this.startColumn = startColumn;
            this.columns = columns;
            this.rows = rows;
        }

        @Override
        public void close()
            throws ApfloatRuntimeException
        {
            if ((this.mode & WRITE) != 0 && getData() != null)
            {
                setArray(this, this.startColumn, this.columns, this.rows);
            }
            super.close();
        }

        private static final long serialVersionUID = 3646716922431352928L;

        private int mode,
                    startColumn,
                    columns,
                    rows;
    }

    private class TransposedMemoryArrayAccess
        extends DoubleMemoryArrayAccess
    {
        public TransposedMemoryArrayAccess(int mode, double[] data, int startColumn, int columns, int rows)
        {
            super(data, 0, data.length);
            this.mode = mode;
            this.startColumn = startColumn;
            this.columns = columns;
            this.rows = rows;
        }

        @Override
        public void close()
            throws ApfloatRuntimeException
        {
            if ((this.mode & WRITE) != 0 && getData() != null)
            {
                setTransposedArray(this, this.startColumn, this.columns, this.rows);
            }
            super.close();
        }

        private static final long serialVersionUID = -3746109883682965310L;

        private int mode,
                    startColumn,
                    columns,
                    rows;
    }

    private class BlockIterator
        extends AbstractIterator
    {
        public BlockIterator(int mode, long startPosition, long endPosition)
            throws IllegalArgumentException, IllegalStateException, ApfloatRuntimeException
        {
            super(mode, startPosition, endPosition);
            this.arrayAccess = null;
            this.remaining = 0;
        }

        @Override
        public void next()
            throws IllegalStateException, ApfloatRuntimeException
        {
            checkLength();

            assert (this.remaining > 0);

            checkAvailable();

            this.offset += getIncrement();
            this.remaining--;

            if (this.remaining == 0)
            {
                close();
            }

            super.next();
        }

        @Override
        public double getDouble()
            throws IllegalStateException, ApfloatRuntimeException
        {
            checkGet();
            checkAvailable();
            return this.data[this.offset];
        }

        @Override
        public void setDouble(double value)
            throws IllegalStateException, ApfloatRuntimeException
        {
            checkSet();
            checkAvailable();
            this.data[this.offset] = value;
        }

        @Override
        public <T> T get(Class<T> type)
            throws UnsupportedOperationException, IllegalStateException
        {
            if (!(type.equals(Double.TYPE)))
            {
                throw new UnsupportedOperationException("Unsupported data type " + type.getCanonicalName() + ", the only supported type is double");
            }
            @SuppressWarnings("unchecked")
            T value = (T) (Double) getDouble();
            return value;
        }

        @Override
        public <T> void set(Class<T> type, T value)
            throws UnsupportedOperationException, IllegalArgumentException, IllegalStateException
        {
            if (!(type.equals(Double.TYPE)))
            {
                throw new UnsupportedOperationException("Unsupported data type " + type.getCanonicalName() + ", the only supported type is double");
            }
            if (!(value instanceof Double))
            {
                throw new IllegalArgumentException("Unsupported value type " + value.getClass().getCanonicalName() + ", the only supported type is Double");
            }
            setDouble((Double) value);
        }

        /**
         * Closes the iterator. This needs to be called only if the
         * iterator is not iterated to the end.
         */

        @Override
        public void close()
            throws ApfloatRuntimeException
        {
            if (this.arrayAccess != null)
            {
                this.data = null;
                this.arrayAccess.close();
                this.arrayAccess = null;
            }
        }

        private void checkAvailable()
            throws ApfloatRuntimeException
        {
            if (this.arrayAccess == null)
            {
                boolean isForward = (getIncrement() > 0);
                int length = (int) Math.min(getLength(), getBlockSize() / 8);
                long offset = (isForward ? getPosition() : getPosition() - length + 1);

                this.arrayAccess = getArray(getMode(), offset, length);
                this.data = this.arrayAccess.getDoubleData();
                this.offset = this.arrayAccess.getOffset() + (isForward ? 0 : length - 1);
                this.remaining = length;
            }
        }

        private static final long serialVersionUID = -1996647087834590031L;

        private ArrayAccess arrayAccess;
        private double[] data;
        private int offset,
                    remaining;
    }

    @Override
    public Iterator iterator(int mode, long startPosition, long endPosition)
        throws IllegalArgumentException, IllegalStateException, ApfloatRuntimeException
    {
        if ((mode & READ_WRITE) == 0)
        {
            throw new IllegalArgumentException("Illegal mode: " + mode);
        }
        return new BlockIterator(mode, startPosition, endPosition);
    }

    @Override
    protected int getUnitSize()
    {
        return 8;
    }

    private static final long serialVersionUID = 342871486421108657L;
}
