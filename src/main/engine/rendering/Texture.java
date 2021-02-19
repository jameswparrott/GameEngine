package main.engine.rendering;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.stb.STBImage.*;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.system.MemoryStack;

public class Texture {

	private int id;
	
	private int width;
	
	private int height;
	
	public Texture(String fileName) {
		
		//this(loadTexture(fileName));
		
		this.id = loadTexture(fileName);
		
	}
	
//	public Texture(int id) {
//		
//		this.id = id;
//			
//	}
	
	public void bind() {
		
		glBindTexture(GL_TEXTURE_2D, 0);
		
	}
	
	public void bind(int samplerSlot){
		
		assert(samplerSlot >= 0 && samplerSlot <= 31);
		
		glActiveTexture(GL_TEXTURE0 + samplerSlot);
		
		glBindTexture(GL_TEXTURE_2D, getID());
		
	}
	
	public int getID() {
		
		return this.id;
		
	}

    
    private int loadTexture(String file) {
    	
        ByteBuffer image;
        
        int id;
        
        String path = "./res/textures/" + file;
        
        try (MemoryStack stack = MemoryStack.stackPush()) {
            /* Prepare image buffers */
            IntBuffer w = stack.mallocInt(1);
            
            IntBuffer h = stack.mallocInt(1);
            
            IntBuffer comp = stack.mallocInt(1);

            /* Load image */
            stbi_set_flip_vertically_on_load(true);
           
            image = stbi_load(path, w, h, comp, 4);
            
            if (image == null) {
            	
                throw new RuntimeException("Failed to load a texture file!" + System.lineSeparator() + stbi_failure_reason());
                
            }

            /* Get width and height of image */
            width = w.get();
           
            height = h.get();
            
			id = glGenTextures();
			
			glBindTexture(GL_TEXTURE_2D, id);

			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
			
//			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
//			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

//			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
//			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
			
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
            
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, image);
			
            }
        
        return id;
        
    }
    
    public int getWidth() {
    	
    	return this.width;
    	
    }
    
    public int getHeight() {
    	
    	return this.height;
    	
    }
	
    public void delete() {
    	
        glDeleteTextures(id);
        
    }
    
}
