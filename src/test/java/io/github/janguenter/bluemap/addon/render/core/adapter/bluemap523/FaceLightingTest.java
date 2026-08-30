/*
 * SPDX-License-Identifier: MIT
 */
package io.github.janguenter.bluemap.addon.render.core.adapter.bluemap523;

import de.bluecolored.bluemap.core.resources.pack.resourcepack.blockstate.Variant;
import de.bluecolored.bluemap.core.util.Direction;
import de.bluecolored.bluemap.core.world.LightData;
import de.bluecolored.bluemap.core.world.block.BlockNeighborhood;
import de.bluecolored.bluemap.core.world.block.ExtendedBlock;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FaceLightingTest {

    @Test
    void exposesTheSharedApi() throws ReflectiveOperationException {
        assertTrue(Modifier.isPublic(FaceLighting.class.getModifiers()));
        assertTrue(Modifier.isPublic(FaceLighting.Sample.class.getModifiers()));
        assertTrue(Modifier.isPublic(FaceLighting.class.getDeclaredMethod(
                "sample", BlockNeighborhood.class, Direction.class, Variant.class, int.class
        ).getModifiers()));
    }

    @Test
    void samplesTheIdentityFaceAndAppliesEachMaximum() {
        BlockNeighborhood block = mock(BlockNeighborhood.class);
        ExtendedBlock faced = mock(ExtendedBlock.class);
        when(block.getLightData()).thenReturn(new LightData(4, 3));
        when(block.getNeighborBlock(1, 0, 0)).thenReturn(faced);
        when(faced.getLightData()).thenReturn(new LightData(11, 7));

        FaceLighting.Sample result = FaceLighting.sample(
                block, Direction.EAST, new Variant(null), 9
        );

        assertEquals(new FaceLighting.Sample(11, 9), result);
        verify(block).getNeighborBlock(1, 0, 0);
    }

    @Test
    void samplesAllSixIdentityDirections() {
        for (Direction direction : Direction.values()) {
            assertSampledOffset(
                    direction,
                    new Variant(null),
                    direction.toVector().getX(),
                    direction.toVector().getY(),
                    direction.toVector().getZ()
            );
        }
    }

    @Test
    void rotatesTheSampledFaceWithTheVariant() {
        assertSampledOffset(Direction.NORTH, new Variant(null, 90F, 0F, 0F), 0, -1, 0);
        assertSampledOffset(Direction.EAST, new Variant(null, 0F, 90F, 0F), 0, 0, 1);
        assertSampledOffset(Direction.UP, new Variant(null, 0F, 0F, 90F), 1, 0, 0);
    }

    @Test
    void appliesEmissionOnlyAsTheBlockLightFloor() {
        BlockNeighborhood block = mock(BlockNeighborhood.class);
        ExtendedBlock faced = mock(ExtendedBlock.class);
        when(block.getLightData()).thenReturn(new LightData(12, 5));
        when(block.getNeighborBlock(0, 1, 0)).thenReturn(faced);
        when(faced.getLightData()).thenReturn(new LightData(4, 7));

        assertEquals(7, FaceLighting.sample(block, Direction.UP, new Variant(null), 2).blocklight());
        assertEquals(7, FaceLighting.sample(block, Direction.UP, new Variant(null), 7).blocklight());
        assertEquals(10, FaceLighting.sample(block, Direction.UP, new Variant(null), 10).blocklight());
        assertEquals(12, FaceLighting.sample(block, Direction.UP, new Variant(null), 10).sunlight());
    }

    private static void assertSampledOffset(
            Direction direction,
            Variant variant,
            int dx,
            int dy,
            int dz
    ) {
        BlockNeighborhood block = mock(BlockNeighborhood.class);
        ExtendedBlock faced = mock(ExtendedBlock.class);
        when(block.getLightData()).thenReturn(new LightData(2, 5));
        when(block.getNeighborBlock(dx, dy, dz)).thenReturn(faced);
        when(faced.getLightData()).thenReturn(new LightData(8, 1));

        assertEquals(
                new FaceLighting.Sample(8, 5),
                FaceLighting.sample(block, direction, variant, 0)
        );
        verify(block).getNeighborBlock(dx, dy, dz);
    }
}
