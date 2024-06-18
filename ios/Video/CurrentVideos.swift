class CurrentVideos {
    private var videos: [NSNumber: WeakVideoRef] = .init()

    class var sharedInstance: CurrentVideos {
        enum Shared {
            static let instance = CurrentVideos()
        }
        return Shared.instance
    }

    func add(video: RCTVideo, for tag: NSNumber) {
        let ref: WeakVideoRef = .init(video)
        videos[tag] = ref
    }

    func video(for tag: NSNumber) -> RCTVideo? {
        videos[tag]?.video
    }

    private func cleanup() {
        for key in videos.keys where videos[key]?.video == nil {
            videos.removeValue(forKey: key)
        }
    }
}
