package dinesh.parmar.skills.models

class ListNode(val value : Int) {
    var next : ListNode?=null

    override fun toString(): String {
        return "[${this.toStringListNode(this)}]"
    }


    private fun toStringListNode(node: ListNode?): String {

        // Base condition
        if (node == null) {
            return ""
        }

        // Last node
        if (node.next == null) {
            return "${node.value}"
        }

        // Recursive call
        return "${node.value},${toStringListNode(node.next)}"
    }

    override fun equals(other: Any?): Boolean {
        return this.value == (other as ListNode?)?.value
    }
    
}