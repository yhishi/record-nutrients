package yoshikii.com.record_nutrients.repository.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass
/** メインデータ */
open class MeaLRealm(
    var date: String = "",
    var mealList: RealmList<Meal> = RealmList()
) : RealmObject()

open class Meal(
    /** 食べた時間 */
    var id: Int = 0,
    /** 食べた時間 */
    var time: String = "",
    /** 品名 */
    var item: String = "",
    /** 量 */
    var amount: Int = 0,
    /** 栄養素 */
    var nutrient: Int? = null,
    /** メモ */
    var memo: String? = null
) : RealmObject()
