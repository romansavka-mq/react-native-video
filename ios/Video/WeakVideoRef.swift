final class WeakVideoRef {
    weak var video: RCTVideo?

    init(_ video: RCTVideo) {
        self.video = video
    }
}
