package de.ruedigermoeller.reallive.impl;


import de.ruedigermoeller.fastcast.remoting.FastCast;
import de.ruedigermoeller.fastcast.util.FCUtils;
import de.ruedigermoeller.reallive.util.fibers.FiberPool;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

import java.util.HashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Copyright (c) 2012, Ruediger Moeller. All rights reserved.
 * <p/>
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * <p/>
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 * <p/>
 * Date: 04.11.13
 * Time: 19:10
 * To change this template use File | Settings | File Templates.
 */
public class TableThreadPool {

    Int2ObjectOpenHashMap<FiberPool> threadMap = new Int2ObjectOpenHashMap<>();

    public FiberPool getExecutor( int id ) {
        synchronized (threadMap) {
            FiberPool executor = threadMap.get(id);
            if ( executor == null ) {
                executor = new FiberPool("table "+id+" mutator thread.");
                threadMap.put(id, executor);
                executor.start();
            }
            return executor;
        }
    }

    public void execute( int id, Runnable toRun ) {
        getExecutor(id).execute(toRun);
    }

}
