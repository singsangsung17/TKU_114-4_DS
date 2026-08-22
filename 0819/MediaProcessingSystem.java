interface Playable {
    void play();
}

interface Compressible {
    int compress();
}

abstract class MediaFile {
    private final String fileName;
    private final int sizeKb;

    MediaFile(String fileName, int sizeKb) {
        this.fileName = fileName == null || fileName.isBlank()
                ? "untitled" : fileName.trim();
        this.sizeKb = Math.max(0, sizeKb);
    }

    String getFileName() {
        return fileName;
    }

    int getSizeKb() {
        return sizeKb;
    }

    abstract String describe();
}

class ImageFile extends MediaFile implements Compressible {
    private final int width;
    private final int height;

    ImageFile(String fileName, int sizeKb, int width, int height) {
        super(fileName, sizeKb);
        this.width = Math.max(0, width);
        this.height = Math.max(0, height);
    }

    @Override
    String describe() {
        return "IMAGE " + getFileName() + " " + width + "x" + height
                + " " + getSizeKb() + "KB";
    }

    @Override
    public int compress() {
        return getSizeKb() * 40 / 100;
    }
}

class AudioFile extends MediaFile implements Playable, Compressible {
    private final int seconds;

    AudioFile(String fileName, int sizeKb, int seconds) {
        super(fileName, sizeKb);
        this.seconds = Math.max(0, seconds);
    }

    @Override
    String describe() {
        return "AUDIO " + getFileName() + " " + seconds + "s "
                + getSizeKb() + "KB";
    }

    @Override
    public void play() {
        System.out.println("  播放音訊：" + getFileName());
    }

    @Override
    public int compress() {
        return getSizeKb() * 60 / 100;
    }
}

class VideoFile extends MediaFile implements Playable, Compressible {
    private final String resolution;

    VideoFile(String fileName, int sizeKb, String resolution) {
        super(fileName, sizeKb);
        this.resolution = resolution == null || resolution.isBlank()
                ? "480p" : resolution.trim();
    }

    @Override
    String describe() {
        return "VIDEO " + getFileName() + " " + resolution + " "
                + getSizeKb() + "KB";
    }

    @Override
    public void play() {
        System.out.println("  播放影片：" + getFileName() + " " + resolution);
    }

    @Override
    public int compress() {
        return getSizeKb() * 30 / 100;
    }
}

public class MediaProcessingSystem {
    static void process(MediaFile file) {
        System.out.println(file.describe());
        if (file instanceof Playable playable) {
            playable.play();
        } else {
            System.out.println("  不支援播放");
        }
        if (file instanceof Compressible compressible) {
            System.out.println("  壓縮後：" + compressible.compress() + "KB");
        } else {
            System.out.println("  不支援壓縮");
        }
    }

    public static void main(String[] args) {
        MediaFile[] files = {
            new ImageFile("cover.png", 2400, 1920, 1080),
            new AudioFile("podcast.mp3", 8600, 1830),
            new VideoFile("lecture.mp4", 154000, "1080p"),
            new ImageFile("   ", -100, -10, 0),
            new VideoFile("clip.mp4", 5200, null)
        };

        for (MediaFile file : files) {
            process(file);
        }
    }
}
