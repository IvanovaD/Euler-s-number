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

import org.apfloat.spi.AdditionBuilder;
import org.apfloat.spi.AdditionStrategy;

/**
 * Creates additions for the specified radix and the <code>long</code> element type.<p>
 *
 * @since 1.7.0
 * @version 1.7.0
 * @author Mikko Tommila
 */

public class LongAdditionBuilder
    implements AdditionBuilder<Long>
{
    /**
     * Default constructor.
     */

    public LongAdditionBuilder()
    {
    }

    public AdditionStrategy<Long> createAddition(int radix)
    {
        AdditionStrategy<Long> additionStrategy = new LongAdditionStrategy(radix);
        return additionStrategy;
    }
}
