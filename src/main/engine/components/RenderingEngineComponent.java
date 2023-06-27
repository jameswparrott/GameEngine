package main.engine.components;

import main.engine.rendering.RenderingEngine;

public class RenderingEngineComponent extends GameComponent {

    private RenderingEngine renderingEngine;

    public RenderingEngineComponent(RenderingEngine renderingEngine) {

        this.renderingEngine = renderingEngine;

    }

    public RenderingEngine getRenderingEngine() {

        return renderingEngine;

    }

}
