package imgui.app.vk;

public class QueueFamilyIndices {
    private Integer graphicsFamily = null;
    private Integer presentFamily = null;

    public boolean isComplete() {
        return graphicsFamily != null && presentFamily != null;
    }

    public Integer getGraphicsFamily() {
        return graphicsFamily;
    }

    public void setGraphicsFamily(Integer graphicsFamily) {
        this.graphicsFamily = graphicsFamily;
    }

    public Integer getPresentFamily() {
        return presentFamily;
    }

    public void setPresentFamily(Integer presentFamily) {
        this.presentFamily = presentFamily;
    }
}
