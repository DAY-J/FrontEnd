```
📦 
├─ app
│  └─ src
│     ├─ androidTest
│     │  └─ java
│     │     └─ com
│     │        └─ dayj
│     │           └─ dayj
│     │              └─ ExampleInstrumentedTest.kt
│     ├─ google-services.json
│     ├─ main
│     │  ├─ AndroidManifest.xml
│     │  ├─ ic_main-playstore.png
│     │  ├─ java
│     │  │  └─ com
│     │  │     └─ example
│     │  │        └─ dayj
│     │  │           ├─ DayJApplication.kt
│     │  │           ├─ MainActivity.kt
│     │  │           ├─ NavigatorScreens.kt
│     │  │           ├─ Screen.kt
│     │  │           ├─ alarm
│     │  │           │  ├─ AlarmCenter.kt
│     │  │           │  ├─ AlarmCenterImpl.kt
│     │  │           │  ├─ AlarmReceiver.kt
│     │  │           │  └─ di
│     │  │           │     └─ AlarmModule.kt
│     │  │           ├─ component
│     │  │           │  ├─ BottomNavItem.kt
│     │  │           │  ├─ BottomNavRoute.kt
│     │  │           │  └─ FriendListItem.kt
│     │  │           ├─ data
│     │  │           │  ├─ DataModule.kt
│     │  │           │  ├─ PreferenceManager.kt
│     │  │           │  └─ repo
│     │  │           │     ├─ AuthRepository.kt
│     │  │           │     ├─ AuthRepositoryImpl.kt
│     │  │           │     ├─ LoginAuthorizationRepository.kt
│     │  │           │     ├─ LoginAuthorizationRepositoryImpl.kt
│     │  │           │     ├─ PlanRepository.kt
│     │  │           │     ├─ PlanRepositoryImpl.kt
│     │  │           │     ├─ StatisticsRepository.kt
│     │  │           │     ├─ StatisticsRepositoryImpl.kt
│     │  │           │     ├─ UserRepository.kt
│     │  │           │     └─ UserRepositoryImpl.kt
│     │  │           ├─ datastore
│     │  │           │  ├─ SelfUserAccountDataStore.kt
│     │  │           │  ├─ SelfUserAccountDataStoreImpl.kt
│     │  │           │  ├─ UserInfo.kt
│     │  │           │  ├─ dataFileStore.kt
│     │  │           │  └─ ext
│     │  │           │     └─ DataStoreExt.kt
│     │  │           ├─ di
│     │  │           │  ├─ AppProviders.kt
│     │  │           │  ├─ AuthModule.kt
│     │  │           │  ├─ EtcRetrofit.kt
│     │  │           │  └─ OriginalRetrofit.kt
│     │  │           ├─ domain
│     │  │           │  └─ usecase
│     │  │           │     ├─ login
│     │  │           │     │  ├─ LoginUseCase.kt
│     │  │           │     │  ├─ LogoutUseCase.kt
│     │  │           │     │  └─ RegisterUseCase.kt
│     │  │           │     ├─ plan
│     │  │           │     │  ├─ CreatePlanUseCase.kt
│     │  │           │     │  ├─ DeletePlanUseCase.kt
│     │  │           │     │  ├─ GetPlanUseCase.kt
│     │  │           │     │  ├─ GetPlansByTagUseCase.kt
│     │  │           │     │  ├─ GetPlansUseCase.kt
│     │  │           │     │  ├─ GetRecommendPlanTagUseCase.kt
│     │  │           │     │  └─ UpdatePlanUseCase.kt
│     │  │           │     ├─ plan_option
│     │  │           │     │  ├─ GetPlanOptionUseCase.kt
│     │  │           │     │  └─ UpdatePlanOptionUseCase.kt
│     │  │           │     ├─ statistics
│     │  │           │     │  └─ GetStatisticsUseCase.kt
│     │  │           │     └─ user
│     │  │           │        └─ GetUsersUseCase.kt
│     │  │           ├─ ext
│     │  │           │  ├─ LocalDataExt.kt
│     │  │           │  └─ findActivity.kt
│     │  │           ├─ network
│     │  │           │  ├─ ApiService.kt
│     │  │           │  ├─ api
│     │  │           │  │  ├─ AuthService.kt
│     │  │           │  │  ├─ PlanOptionService.kt
│     │  │           │  │  ├─ PlanService.kt
│     │  │           │  │  ├─ StatisticsService.kt
│     │  │           │  │  ├─ UserService.kt
│     │  │           │  │  ├─ adapter
│     │  │           │  │  │  └─ StatisticsDateJsonAdapter.kt
│     │  │           │  │  └─ response
│     │  │           │  │     ├─ PlanOption.kt
│     │  │           │  │     ├─ PlanOptionRequest.kt
│     │  │           │  │     ├─ PlanRequest.kt
│     │  │           │  │     ├─ PlanResponse.kt
│     │  │           │  │     ├─ PlanTag.kt
│     │  │           │  │     └─ StatisticsDate.kt
│     │  │           │  └─ util
│     │  │           │     ├─ AuthAuthenticator.kt
│     │  │           │     └─ RequestInterceptor.kt
│     │  │           ├─ ui
│     │  │           │  ├─ friends
│     │  │           │  │  ├─ data
│     │  │           │  │  │  ├─ model
│     │  │           │  │  │  │  ├─ request
│     │  │           │  │  │  │  │  ├─ RequestGroupCreation.kt
│     │  │           │  │  │  │  │  ├─ RequestGroupGoal.kt
│     │  │           │  │  │  │  │  └─ RequestGroupName.kt
│     │  │           │  │  │  │  └─ response
│     │  │           │  │  │  │     └─ ResponseFriendGroups.kt
│     │  │           │  │  │  └─ source
│     │  │           │  │  │     ├─ FriendsDataSource.kt
│     │  │           │  │  │     └─ FriendsDataSourceImpl.kt
│     │  │           │  │  ├─ domain
│     │  │           │  │  │  ├─ entity
│     │  │           │  │  │  │  ├─ FriendsGroupEntity.kt
│     │  │           │  │  │  │  ├─ GoalByParicipantEntity.kt
│     │  │           │  │  │  │  ├─ GroupParticipantEntity.kt
│     │  │           │  │  │  │  └─ UserEntity.kt
│     │  │           │  │  │  └─ repository
│     │  │           │  │  │     ├─ FriendsRepository.kt
│     │  │           │  │  │     └─ FriendsRepositoryImpl.kt
│     │  │           │  │  └─ presentation
│     │  │           │  │     ├─ FriendsContainerScreen.kt
│     │  │           │  │     ├─ groupdetail
│     │  │           │  │     │  ├─ EditGroupGoalDialog.kt
│     │  │           │  │     │  ├─ ExitGroupDialog.kt
│     │  │           │  │     │  ├─ GroupDetailScreen.kt
│     │  │           │  │     │  ├─ GroupDetailViewModel.kt
│     │  │           │  │     │  ├─ GroupSettingDialog.kt
│     │  │           │  │     │  └─ InviteFriendDialog.kt
│     │  │           │  │     └─ groups
│     │  │           │  │        ├─ CreateGroupDialog.kt
│     │  │           │  │        ├─ FriendGroupListScreen.kt
│     │  │           │  │        └─ GroupListViewModel.kt
│     │  │           │  ├─ home
│     │  │           │  │  ├─ HomeScreen.kt
│     │  │           │  │  ├─ HomeViewModel.kt
│     │  │           │  │  ├─ HomeViewState.kt
│     │  │           │  │  ├─ TodoEntity.kt
│     │  │           │  │  ├─ addtodo
│     │  │           │  │  │  ├─ AddTodoViewModel.kt
│     │  │           │  │  │  ├─ TodoScreen.kt
│     │  │           │  │  │  ├─ TodoViewEffect.kt
│     │  │           │  │  │  ├─ TodoViewState.kt
│     │  │           │  │  │  └─ UpdateTodoViewModel.kt
│     │  │           │  │  └─ component
│     │  │           │  │     ├─ PlanTagSelector.kt
│     │  │           │  │     ├─ TodoItem.kt
│     │  │           │  │     ├─ TodoList.kt
│     │  │           │  │     ├─ TodoSwitch.kt
│     │  │           │  │     ├─ calendar
│     │  │           │  │     │  ├─ CalendarDate.kt
│     │  │           │  │     │  ├─ CalendarDay.kt
│     │  │           │  │     │  ├─ CalendarHeader.kt
│     │  │           │  │     │  ├─ CalendarMonth.kt
│     │  │           │  │     │  ├─ CalendarSheet.kt
│     │  │           │  │     │  └─ HorizontalCalendar.kt
│     │  │           │  │     └─ todo
│     │  │           │  │        ├─ PastOrPresentSelectableDates.kt
│     │  │           │  │        ├─ PlanOptionType.kt
│     │  │           │  │        ├─ RecommendItem.kt
│     │  │           │  │        ├─ RepeatDatePicker.kt
│     │  │           │  │        ├─ RepeatDayOfWeek.kt
│     │  │           │  │        ├─ RepeatDayType.kt
│     │  │           │  │        ├─ TodoOptions.kt
│     │  │           │  │        └─ TodoTitle.kt
│     │  │           │  ├─ login
│     │  │           │  │  ├─ LoginEffect.kt
│     │  │           │  │  ├─ LoginScreen.kt
│     │  │           │  │  └─ LoginViewModel.kt
│     │  │           │  ├─ lounge
│     │  │           │  │  ├─ data
│     │  │           │  │  │  ├─ model
│     │  │           │  │  │  │  ├─ RequestCommentModel.kt
│     │  │           │  │  │  │  ├─ RequestPostingCreation.kt
│     │  │           │  │  │  │  └─ ResponsePosting.kt
│     │  │           │  │  │  └─ source
│     │  │           │  │  │     ├─ LoungeDataSource.kt
│     │  │           │  │  │     └─ LoungeDataSourceImpl.kt
│     │  │           │  │  ├─ domain
│     │  │           │  │  │  ├─ LoungeTagEnum.kt
│     │  │           │  │  │  ├─ entity
│     │  │           │  │  │  │  ├─ CommentEntity.kt
│     │  │           │  │  │  │  └─ LoungePostingEntity.kt
│     │  │           │  │  │  └─ repository
│     │  │           │  │  │     ├─ LoungeRepository.kt
│     │  │           │  │  │     └─ LoungeRepositoryImpl.kt
│     │  │           │  │  └─ presentation
│     │  │           │  │     ├─ lounge
│     │  │           │  │     │  ├─ LoungeItemView.kt
│     │  │           │  │     │  ├─ LoungeScreen.kt
│     │  │           │  │     │  ├─ LoungeSortDialog.kt
│     │  │           │  │     │  ├─ LoungeTag.kt
│     │  │           │  │     │  ├─ LoungeViewModel.kt
│     │  │           │  │     │  ├─ PostSettingDialog.kt
│     │  │           │  │     │  └─ SearchPostingScreen.kt
│     │  │           │  │     ├─ posting
│     │  │           │  │     │  ├─ LoungePostingViewModel.kt
│     │  │           │  │     │  └─ PostingScreen.kt
│     │  │           │  │     └─ write
│     │  │           │  │        ├─ PostWritingScreen.kt
│     │  │           │  │        └─ PostWritingViewModel.kt
│     │  │           │  ├─ mypage
│     │  │           │  │  ├─ data
│     │  │           │  │  │  ├─ RequestModifyNickName.kt
│     │  │           │  │  │  ├─ UserDataSource.kt
│     │  │           │  │  │  └─ UserDataSourceImpl.kt
│     │  │           │  │  └─ presentation
│     │  │           │  │     ├─ ChangeNickNameScreen.kt
│     │  │           │  │     ├─ LinkedAccountScreen.kt
│     │  │           │  │     ├─ MyPageScreen.kt
│     │  │           │  │     └─ MyPageViewModel.kt
│     │  │           │  ├─ register
│     │  │           │  │  ├─ RegisterEffect.kt
│     │  │           │  │  ├─ RegisterScreen.kt
│     │  │           │  │  ├─ RegisterState.kt
│     │  │           │  │  └─ RegisterViewModel.kt
│     │  │           │  ├─ statistics
│     │  │           │  │  ├─ StatisticsScreen.kt
│     │  │           │  │  ├─ StatisticsViewModel.kt
│     │  │           │  │  ├─ StatisticsViewState.kt
│     │  │           │  │  └─ component
│     │  │           │  │     ├─ StatisticsDateDialog.kt
│     │  │           │  │     ├─ StatisticsResult.kt
│     │  │           │  │     └─ StatisticsTitle.kt
│     │  │           │  └─ theme
│     │  │           │     ├─ Color.kt
│     │  │           │     ├─ Theme.kt
│     │  │           │     └─ Type.kt
│     │  │           └─ util
│     │  │              ├─ BuildConfigFieldUtils.kt
│     │  │              ├─ DateUtils.kt
│     │  │              ├─ Extensions.kt
│     │  │              ├─ NavigationUtil.kt
│     │  │              ├─ Reflect.kt
│     │  │              ├─ Result.kt
│     │  │              ├─ UseCaseUtil.kt
│     │  │              ├─ collectInLaunchedEffect.kt
│     │  │              └─ toResult.kt
│     └─ test
│        └─ java
│           └─ com
│              └─ dayj
│                 └─ dayj
│                    └─ ExampleUnitTest.kt
├─ build.gradle.kts
├─ gradle.properties
├─ gradle
│  └─ wrapper
│     ├─ gradle-wrapper.jar
│     └─ gradle-wrapper.properties
├─ gradlew
├─ gradlew.bat
└─ settings.gradle.kts
```
