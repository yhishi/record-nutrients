package yoshikii.com.record_nutrients

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.RealmClass
import java.util.*

@RealmClass
/** メインデータ */
open class NutrientRealm(
    var proteinSpinner: RealmList<Nutrient> = RealmList(),
    var sugarSpinner: RealmList<Nutrient> = RealmList(),
    var recordNutrient: RealmList<RecordNutrient> = RealmList()
) : RealmObject()

open class Nutrient(
    /** 栄養素名 */
    var item: String? = null,
    /** 値 */
    var value: Int? = null
) : RealmObject()

/** 毎日の記録 */
open class RecordNutrient(
    /** 日付 */
    var date: Date? = null,
    /** 朝 タンパク質 */
    var morningProtein: Int? = null,
    /** 朝 炭水化物 */
    var morningSugar: Int? = null,
    /** 間食1 タンパク質 */
    var betweenFirstProtein: Int? = null,
    /** 間食1 炭水化物 */
    var betweenFirstSugar: Int? = null,
    /** 昼 タンパク質 */
    var lunchProtein: Int? = null,
    /** 昼 炭水化物 */
    var lunchSugar: Int? = null,
    /** 間食2 タンパク質 */
    var betweenSecondProtein: Int? = null,
    /** 間食2 炭水化物 */
    var betweenSecondSugar: Int? = null,
    /** 夜 タンパク質 */
    var dinnerProtein: Int? = null,
    /** 夜 炭水化物 */
    var dinnerSugar: Int? = null,
    /** 就寝前 タンパク質 */
    var bedProtein: Int? = null
) : RealmObject()

