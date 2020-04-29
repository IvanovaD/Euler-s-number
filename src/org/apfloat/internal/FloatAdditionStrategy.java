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

import org.apfloat.ApfloatRuntimeException;
import org.apfloat.spi.AdditionStrategy;
import org.apfloat.spi.DataStorage.Iterator;

/**
 * Basic addition strategy for the <code>float</code> element type.
 *
 * @since 1.7.0
 * @version 1.7.0
 * @author Mikko Tommila
 */
public class FloatAdditionStrategy
    extends FloatBaseMath
    implements AdditionStrategy<Float>
{
    /**
     * Creates an addition strategy using the specified radix.
     *
     * @param radix The radix that will be used.
     */

    public FloatAdditionStrategy(int radix)
    {
        super(radix);
    }

    public Float add(Iterator src1, Iterator src2, Float carry, Iterator dst, long size)
        throws ApfloatRuntimeException
    {
        return baseAdd(src1, src2, carry, dst, size);
    }

    public Float subtract(Iterator src1, Iterator src2, Float carry, Iterator dst, long size)
        throws ApfloatRuntimeException
    {
        return baseSubtract(src1, src2, carry, dst, size);
    }

    public Float multiplyAdd(Iterator src1, Iterator src2, Float src3, Float carry, Iterator dst, long size)
        throws ApfloatRuntimeException
    {
        return baseMultiplyAdd(src1, src2, src3, carry, dst, size);
    }

    public Float divide(Iterator src1, Float src2, Float carry, Iterator dst, long size)
        throws ApfloatRuntimeException
    {
        return baseDivide(src1, src2, carry, dst, size);
    }

    public Float zero()
    {
        return (float) 0;
    }

    private static final long serialVersionUID = -8811571288007744481L;
}
