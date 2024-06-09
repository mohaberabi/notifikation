import SwiftUI
import UserNotifications

@main
struct iOSApp: App {
	var body: some Scene {
		WindowGroup {
			ContentView().onAppear{
setupNotifications()
			}
		}
	}
	 func setupNotifications() {
            UNUserNotificationCenter.current().requestAuthorization(options: [.alert, .sound, .badge]) { granted, error in
                if let error = error {
                    print("Error requesting notification authorization: \(error.localizedDescription)")
                    return
                }
                if granted {
                    print("Notification permissions granted")
                } else {
                    print("Notification permissions denied")
                }
            }
        }
}