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

import org.apfloat.spi.ConvolutionStrategy;
import org.apfloat.spi.NTTStrategy;
import static org.apfloat.internal.FloatConstants.*;

/**
 * Creates convolutions of suitable type for the <code>float</code> type.<p>
 *
 * @see FloatShortConvolutionStrategy
 * @see FloatMediumConvolutionStrategy
 * @see FloatKaratsubaConvolutionStrategy
 * @see ThreeNTTConvolutionStrategy
 *
 * @version 1.7.0
 * @author Mikko Tommila
 */

public class FloatConvolutionBuilder
    extends AbstractConvolutionBuilder
{
    /**
     * Default constructor.
     */

    public FloatConvolutionBuilder()
    {
    }

    @Override
    protected int getKaratsubaCutoffPoint()
    {
        return FloatKaratsubaConvolutionStrategy.CUTOFF_POINT;
    }

    @Override
    protected float getKaratsubaCostFactor()
    {
        return KARATSUBA_COST_FACTOR;
    }

    @Override
    protected float getNTTCostFactor()
    {
        return NTT_COST_FACTOR;
    }

    @Override
    protected ConvolutionStrategy createShortConvolutionStrategy(int radix)
    {
        return new FloatShortConvolutionStrategy(radix);
    }

    @Override
    protected ConvolutionStrategy createMediumConvolutionStrategy(int radix)
    {
        return new FloatMediumConvolutionStrategy(radix);
    }

    @Override
    protected ConvolutionStrategy createKaratsubaConvolutionStrategy(int radix)
    {
        return new FloatKaratsubaConvolutionStrategy(radix);
    }

    @Override
    protected ConvolutionStrategy createThreeNTTConvolutionStrategy(int radix, NTTStrategy nttStrategy)
    {
        return new ParallelThreeNTTConvolutionStrategy(radix, nttStrategy);
    }
}
