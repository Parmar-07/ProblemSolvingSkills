package dp.problemsovle.skills.framework

import dp.problemsovle.skills.framework.registry.ProblemRegistry

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {

    // ── Register all problems ──────────────────────────────────────────────────
    // Add new problems here. Order determines README table insertion order.
    ProblemRegistry.registerAll()

}