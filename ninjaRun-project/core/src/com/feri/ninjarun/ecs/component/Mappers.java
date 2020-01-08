package com.feri.ninjarun.ecs.component;

import com.badlogic.ashley.core.ComponentMapper;
//TODO Explain how Mappers work (see ComponentMapper and Entity implementation)
public final class Mappers {

    public static final ComponentMapper<BoundsComponent> BOUNDS =
            ComponentMapper.getFor(BoundsComponent.class);
    public static final ComponentMapper<DimensionComponent> DIMENSION =
            ComponentMapper.getFor(DimensionComponent.class);

    public static final ComponentMapper<PositionComponent> POSITION =
            ComponentMapper.getFor(PositionComponent.class);
    public static final ComponentMapper<GravityComponent> GRAVITIY =
            ComponentMapper.getFor(GravityComponent.class);
    public static final ComponentMapper<SkierComponent> SKIER =
            ComponentMapper.getFor(SkierComponent.class);
    public static final ComponentMapper<MovementComponentXYR> MOVEMENT =
            ComponentMapper.getFor(MovementComponentXYR.class);

    public static final ComponentMapper<TransformComponent> TRANSFORM =
            ComponentMapper.getFor(TransformComponent.class);

    public static final ComponentMapper<TextureComponent> TEXTURE =
            ComponentMapper.getFor(TextureComponent.class);

    public static final ComponentMapper<ZOrderComponent> Z_ORDER =
            ComponentMapper.getFor(ZOrderComponent.class);

    private Mappers() {
    }
}
